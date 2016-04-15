package com.encriptionapp.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.encriptionapp.R;
import com.encriptionapp.utils.GetDeviceResolution;
import com.encriptionapp.utils.Utility;

public class MainActivity extends Activity {

    private EditText _etString;
    private Button _btnSearch;
    private TextView _txtResult, _txtHashString;
    private long code;
    private int height, width;
    private double screenInches;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        initView();
        initListner();
    }

    private void initView(){
        GetDeviceResolution ds = new GetDeviceResolution(this);
        height = ds.height;
        width = ds.width;
        screenInches = ds.screenInches;
        ds = null;
        _etString = (EditText) findViewById(R.id.etString);
        _btnSearch = (Button) findViewById(R.id.btnSearch);
        _txtResult =(TextView) findViewById(R.id.txtResult);
        _txtHashString = (TextView)findViewById(R.id.txtHashString);

        LinearLayout.LayoutParams llParam = new LinearLayout.LayoutParams((int)(width * 50)/100, (int)(height * 6)/100);
        _etString.setLayoutParams(llParam);
        _etString.setPadding((int) (width * 3) / 100, 0, (int) (width * 3) / 100, 0);
        _etString.setTextSize(TypedValue.COMPLEX_UNIT_SP, (int) (screenInches * 4));

        llParam = new LinearLayout.LayoutParams((int)(width * 50)/100, (int)(height * 6)/100);
        llParam.setMargins(0, (int) (height * 4) / 100, 0, 0);
        _btnSearch.setLayoutParams(llParam);
        _btnSearch.setTextSize(TypedValue.COMPLEX_UNIT_SP, (int) (screenInches * 4));

        ((TextView)findViewById(R.id.txtNote)).setPadding((int) (width * 4) / 100, 0, (int) (width * 4) / 100, 0);
        ((TextView)findViewById(R.id.txtNote)).setTextSize(TypedValue.COMPLEX_UNIT_SP, (int) (screenInches * 4));

        _txtHashString.setPadding((int) (width * 4) / 100, 0, (int) (width * 4) / 100, (int) (height * 2) / 100);
        _txtHashString.setTextSize(TypedValue.COMPLEX_UNIT_SP, (int) (screenInches * 4));

        _txtResult.setPadding((int) (width * 4) / 100, 0, (int) (width * 4) / 100, 0);
        _txtResult.setTextSize(TypedValue.COMPLEX_UNIT_SP, (int) (screenInches * 4));
    }

    private void initListner(){
        _btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(_etString.getText().toString().trim().length()>0){
                    _txtHashString.setText("");
                    _txtResult.setText("");
                    code = Long.valueOf(_etString.getText().toString().trim());
                    new EncriptAsync().execute(code);
                }else{
                    Toast.makeText(getApplicationContext(), "Enter your hash.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    class EncriptAsync extends AsyncTask<Long, String, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setCancelable(false);
            pDialog.setMessage("Please Wait...");
            pDialog.show();
        }

        @Override
        protected String doInBackground(Long... params) {
            String s;
            for (s = "aaaaa"; Utility.hash(s) != params[0].longValue(); s = Utility.nextString(s)) {
                if(s.equals("wrong"))
                    break;
            }
            return s;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pDialog.dismiss();
            if(s.equals("wrong")) {
                _txtResult.setText((new StringBuilder()).append("Given hash '").append(code).append("' is wrong. Please check your hash.").toString());
            }else{
                _txtHashString.setText(s);
                _txtResult.setText((new StringBuilder()).append("Given hash '").append(code).append("' is from String '").append(s).append("'.").toString());
            }
        }
    }

}
