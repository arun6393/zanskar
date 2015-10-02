/*
 * Basic no frills app which integrates the ZBar barcode scanner with
 * the camera.
 * 
 * Created by lisah0 on 2012-02-24
 */
package com.example.femion_3.zanskar.QrScan;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.femion_3.zanskar.Main.NavigationDrawer;
import com.example.femion_3.zanskar.R;

import net.sourceforge.zbar.Config;
import net.sourceforge.zbar.Image;
import net.sourceforge.zbar.ImageScanner;
import net.sourceforge.zbar.Symbol;
import net.sourceforge.zbar.SymbolSet;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

/* Import ZBar Class files */

public class CameraTestActivity extends Activity
{
    private static final String PREF_NAME1 = "code";
    private Camera mCamera;
    private CameraPreview mPreview;
    private Handler autoFocusHandler;
    String barcode;
    TextView scanText;
    Button scanButton;
    String Uid,Name,Address,Address1,Faddress,gender;
    String[] fname;
    ImageScanner scanner;

    private boolean barcodeScanned = false;
    private boolean previewing = true;

//    static {
//        System.loadLibrary("iconv");
//    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);
       // unsetpreference();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        autoFocusHandler = new Handler();
        mCamera = getCameraInstance();

        /* Instance barcode scanner */
        scanner = new ImageScanner();
        scanner.setConfig(0, Config.X_DENSITY, 3);
        scanner.setConfig(0, Config.Y_DENSITY, 3);

        mPreview = new CameraPreview(this, mCamera, previewCb, autoFocusCB);
        FrameLayout preview = (FrameLayout)findViewById(R.id.cameraPreview);
        preview.addView(mPreview);

        scanText = (TextView)findViewById(R.id.scanText);

        scanButton = (Button)findViewById(R.id.ScanButton);

        scanButton.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (barcodeScanned) {
                        barcodeScanned = false;
                        scanText.setText("Scanning...");
                        mCamera.setPreviewCallback(previewCb);
                        mCamera.startPreview();
                        previewing = true;
                        mCamera.autoFocus(autoFocusCB);
                    }
                }
            });
    }

    private void unsetpreference() {
        SharedPreferences settings = getSharedPreferences(NavigationDrawer.PREFS_NAME, 0); // 0 - for private mode
        SharedPreferences.Editor editor = settings.edit();
       // System.out.println("NAME::::"+name[0]);
        editor.putString("name1", "null");
        editor.putString("lname", "null");
        editor.putString("address", "null");
        editor.putString("uid", "null");
        editor.putString("set", "null");
        editor.putString("sex", "null");
        editor.commit();
    }

    public void onPause() {
        super.onPause();
        releaseCamera();
    }

    /** A safe way to get an instance of the Camera object. */
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open();
        } catch (Exception e){
        }
        return c;
    }

    private void releaseCamera() {
        if (mCamera != null) {
            previewing = false;
            mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera = null;
        }
    }

    private Runnable doAutoFocus = new Runnable() {
            public void run() {
                if (previewing)
                    mCamera.autoFocus(autoFocusCB);
            }
        };

    PreviewCallback previewCb = new PreviewCallback() {
            public void onPreviewFrame(byte[] data, Camera camera) {
                Parameters parameters = camera.getParameters();
                Size size = parameters.getPreviewSize();

                Image barcode = new Image(size.width, size.height, "Y800");
                barcode.setData(data);

                int result = scanner.scanImage(barcode);
                
                if (result != 0) {
                    previewing = false;
                    mCamera.setPreviewCallback(null);
                    mCamera.stopPreview();
                    
                    SymbolSet syms = scanner.getResults();
                    for (Symbol sym : syms) {

                        Log.i("scanner data",sym.getData());
                        try {
                            Xmlparse(sym.getData());
                        } catch (ParserConfigurationException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (SAXException e) {
                            e.printStackTrace();
                        } catch (XPathExpressionException e) {
                            e.printStackTrace();
                        }


                    }
                }
            }
        };

    private void Xmlparse(String data) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {

        InputSource source = new InputSource(new StringReader(data));

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.parse(source);

        XPathFactory xpathFactory = XPathFactory.newInstance();
        XPath xpath = xpathFactory.newXPath();
        System.out.println("document value is"+document);
        Uid = xpath.evaluate("/PrintLetterBarcodeData/@uid", document);
       Name = xpath.evaluate("/PrintLetterBarcodeData/@name", document);
         Address=xpath.evaluate("/PrintLetterBarcodeData/@loc", document);
         Address1=xpath.evaluate("/PrintLetterBarcodeData/@lm", document);
        gender=xpath.evaluate("/PrintLetterBarcodeData/@gender", document);
       Faddress=Address+"\n"+Address1;
        fname=Name.split(" ");

        System.out.println("name is"+ Name  + ";" + "Uid=" + Uid +";" + "address is"+Faddress);
        System.out.println("fname is"+fname[0]);
if(!Name.equals("") && !Faddress.equals("") && !gender.equals("") && !Uid.equals(""))
        {
            setpreference(fname, Faddress, Uid, gender);
            scanText.setText("DONE");
            barcodeScanned = true;

            // setsharedpreference(scanText.getText().toString());
            Intent x=new Intent(getApplicationContext(),NavigationDrawer.class);
            x.putExtra("scaneddata",11);
            startActivity(x);
        }else
        {
            scanText.setText("error");
        }
    }

    private void setpreference(String[] name, String faddress, String uid,String gender) {
        SharedPreferences settings = getSharedPreferences(NavigationDrawer.PREFS_NAME, 0); // 0 - for private mode
        SharedPreferences.Editor editor = settings.edit();
        System.out.println("NAME::::"+name[0]);
        editor.putString("name1", name[0]);
        editor.putString("lname",name[2]);
        editor.putString("address",faddress);
        editor.putString("uid", uid);
        editor.putString("set","isset");
        editor.putString("sex",gender);
        editor.commit();
    }

//    private void setsharedpreference(String data) {
//
//
//
//
//        editor.putString("code",data);
//
//        editor.commit();
//    }

    public String getString() {
        return scanText.getText().toString();

    }

    // Mimic continuous auto-focusing
    AutoFocusCallback autoFocusCB = new AutoFocusCallback() {
            public void onAutoFocus(boolean success, Camera camera) {
                autoFocusHandler.postDelayed(doAutoFocus, 1000);
            }
        };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i4=new Intent(this,NavigationDrawer.class);
        i4.putExtra("noscan",100);
        startActivity(i4);
    }
}
