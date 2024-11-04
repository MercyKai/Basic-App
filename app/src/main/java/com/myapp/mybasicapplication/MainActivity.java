package com.myapp.mybasicapplication;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import android.net.Uri;


public class MainActivity extends AppCompatActivity {
    String TAG = "Events";

    int request_Code = 1;

    Button b1, b2, b3, b4, b5;

    CharSequence[] items = {"Table", "Coffee", "Blue"};
    boolean[] itemsChecked = new boolean[items.length];
    private ProgressDialog _progressDialog;
    private int _progress = 0;
    private Handler _progressHandler;

    private static final String INTENT_ACTION = "com.myapp.mybasicapplication.ACTIVITY2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = (Button) findViewById(R.id.btn_webbrowser);
        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent i = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://www.jumia.com"));
                startActivity(i);
            }
        });

        b2 = (Button) findViewById(R.id.btn_makecalls);
        b2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent i = new Intent(android.content.Intent.ACTION_DIAL,
                        Uri.parse("tel:+1234567890"));
                startActivity(i);
            }
        });

        b3 = (Button) findViewById(R.id.btn_showMap);
        b3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent i = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("geo:37.827500,-122.481670"));
                startActivity(i);
            }
        });

        b4 = (Button) findViewById(R.id.btn_chooseContact);
        b4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent i = new Intent(android.content.Intent.ACTION_PICK);
                i.setType(ContactsContract.Contacts.CONTENT_TYPE);
                startActivityForResult(i, request_Code);
            }
        });

        b5 = (Button) findViewById(R.id.btn_launchMyBrowser);
        b5.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View arg0)
            {
                Intent i = new Intent("com.myapp.mybasicapplication.MyBrowser");
                i.setData(Uri.parse("http://www.google.com"));
                startActivity(i);
            }
        });

        TextView helloTextView = findViewById(R.id.helloTextView);
        TextView infoTextView = findViewById(R.id.infoTextView);
        Button clickButton = findViewById(R.id.clickButton);
        Button btn = findViewById(R.id.btn_dialog);

        clickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showActivitySelectionDialog();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(0);
                showDialog(1);
                _progress = 0;
                _progressDialog.setProgress(0);
                _progressHandler.sendEmptyMessage(0);
            }
        });

        _progressHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (_progress >= 100) {
                    _progressDialog.dismiss();
                } else {
                    _progress++;
                    _progressDialog.incrementProgressBy(1);
                    _progressHandler.sendEmptyMessageDelayed(0, 100);
                }
            }
        };

        Log.d(TAG, "In the onCreate() event");
    }

    private void showActivitySelectionDialog() {
        final CharSequence[] activities = {"Area and Perimeter", "Random Number"};

        new AlertDialog.Builder(this)
                .setTitle("Choose an Activity")
                .setSingleChoiceItems(activities, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(INTENT_ACTION);

                        if (which == 0) {
                            intent.setClass(MainActivity.this, Activity2.class);
                        } else if (which == 1) {
                            intent.setClass(MainActivity.this, Activity3.class);
                        }

                        startActivity(intent);
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("OK", null)
                .setNegativeButton("Cancel", null)
                .setNeutralButton("SET AS DEFAULT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "Default activity set!", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
            startActivity(new Intent(this, Activity2.class));

            startActivityForResult(new Intent("com.myapp.mybasicapplication.ACTIVITY2"), request_Code);

            Intent i = new Intent("com.myapp.mybasicapplication.ACTIVITY2");
            Bundle extras = new Bundle();
            extras.putString("Name", "Your name here");
            i.putExtras(extras);
            startActivityForResult(i, 1);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == request_Code) {
            if (resultCode == RESULT_OK && data != null) {
                Toast.makeText(this,data.getData().toString(),
                        Toast.LENGTH_SHORT).show();

                Intent i = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse(data.getData().toString()));
                startActivity(i);
            }
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case 0:
                return new AlertDialog.Builder(this)
                        .setIcon(R.drawable.icon)
                        .setTitle("Random Objects...")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Toast.makeText(getBaseContext(), "OK!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Toast.makeText(getBaseContext(), "Cancelled!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setMultiChoiceItems(items, itemsChecked, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                Toast.makeText(getBaseContext(),
                                        items[which] + (isChecked ? " checked!" : " unchecked!"),
                                        Toast.LENGTH_SHORT).show();
                            }
                        })
                        .create();
            case 1:
                _progressDialog = new ProgressDialog(this);
                _progressDialog.setIcon(R.drawable.icon);
                _progressDialog.setTitle("Loading...");
                _progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                _progressDialog.setIndeterminate(false);
                _progressDialog.setMax(100);

                _progressDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Hide", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Toast.makeText(getBaseContext(), "Hide clicked!", Toast.LENGTH_SHORT).show();
                    }
                });

                _progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Toast.makeText(getBaseContext(), "Cancelled!", Toast.LENGTH_SHORT).show();
                    }
                });

                return _progressDialog;
        }
        return null;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "In the onStart() event");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "In the onRestart() event");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "In the onResume() event");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "In the onPause() event");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "In the onStop() event");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "In the onDestroy() event");
    }
}
