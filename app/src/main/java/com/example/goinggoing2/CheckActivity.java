package com.example.goinggoing2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.scottyab.HeartBeatView;

import java.util.ArrayList;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;

public class CheckActivity extends AppCompatActivity {
    private HeartBeatView heartbeat;
    private EditText et1;
    private Button btnInput;
    private TextView tv3;
    private TextView tv4;
    private TextView tv5;
    private int bpm = 74;
    private int age2;
    private int s2;

    private ArrayList<Integer> bpm_buf = new ArrayList<Integer>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        tv3 = findViewById(R.id.textView3);
        tv4 = findViewById(R.id.textView4);
        tv5 = findViewById(R.id.textView5);
        heartbeat = findViewById(R.id.heartbeat);
        et1 = findViewById(R.id.et1);
        btnInput = findViewById(R.id.btnInput);
        heartbeat.setDurationBasedOnBPM(bpm);
        bpm_buf.add(bpm);
        heartbeat.start(); // or toggle(), start(), stop()
        Intent intent = getIntent();
        age2 = intent.getIntExtra("나이", 20);
        s2 = intent.getIntExtra("성별", 2);

        ((MainActivity)MainActivity.context_main).bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() {
            @Override
            public void onDataReceived(byte[] data, String message) {
                int bpm = Integer.parseInt(message);
                heartbeat.setDurationBasedOnBPM(bpm);
                bpm_buf.add(bpm);
                tv3.setText("" + bpm + " bpm");
                int temp = 0;
                for (int i = 0; i < bpm_buf.size(); i++) {
                    temp += bpm_buf.get(i);
                }
                temp = temp / bpm_buf.size();
                tv5.setText("" + temp + " bpm");

                if (s2 == 1) {
                    if (age2 <= 25 && age2 >= 18) {
                        if (temp <= 55 && temp >= 49) {
                            tv4.setText("매우건강");
                        } else if (temp <= 69 && temp >= 56) {
                            tv4.setText("건강");
                        } else if (temp <= 73 && temp >= 70) {
                            tv4.setText("평균");
                        } else if (temp <= 81 && temp >= 74) {
                            tv4.setText("평균 이하");
                        } else if (temp >= 82) {
                            tv4.setText("나쁨");
                        }
                    } else if (age2 <= 35 && age2 >= 26) {
                        if (temp <= 54 && temp >= 49) {
                            tv4.setText("매우건강");
                        } else if (temp <= 70 && temp >= 55) {
                            tv4.setText("건강");
                        } else if (temp <= 74 && temp >= 71) {
                            tv4.setText("평균");
                        } else if (temp <= 81 && temp >= 75) {
                            tv4.setText("평균 이하");
                        } else if (temp >= 82) {
                            tv4.setText("나쁨");
                        }
                    } else if (age2 <= 45 && age2 >= 36) {
                        if (temp <= 56 && temp >= 50) {
                            tv4.setText("매우건강");
                        } else if (temp <= 70 && temp >= 55) {
                            tv4.setText("건강");
                        } else if (temp <= 75 && temp >= 71) {
                            tv4.setText("평균");
                        } else if (temp <= 82 && temp >= 75) {
                            tv4.setText("평균 이하");
                        } else if (temp >= 83) {
                            tv4.setText("나쁨");
                        }
                    } else if (age2 <= 55 && age2 >= 46) {
                        if (temp <= 57 && temp >= 50) {
                            tv4.setText("매우건강");
                        } else if (temp <= 71 && temp >= 58) {
                            tv4.setText("건강");
                        } else if (temp <= 76 && temp >= 72) {
                            tv4.setText("평균");
                        } else if (temp <= 83 && temp >= 77) {
                            tv4.setText("평균 이하");
                        } else if (temp >= 84) {
                            tv4.setText("나쁨");
                        }
                    } else if (age2 <= 65 && age2 >= 56) {
                        if (temp <= 56 && temp >= 51) {
                            tv4.setText("매우건강");
                        } else if (temp <= 71 && temp >= 57) {
                            tv4.setText("건강");
                        } else if (temp <= 75 && temp >= 72) {
                            tv4.setText("평균");
                        } else if (temp <= 81 && temp >= 76) {
                            tv4.setText("평균 이하");
                        } else if (temp >= 82) {
                            tv4.setText("나쁨");
                        }
                    } else if (age2 >= 66) {
                        if (temp <= 55 && temp >= 50) {
                            tv4.setText("매우건강");
                        } else if (temp <= 69 && temp >= 56) {
                            tv4.setText("건강");
                        } else if (temp <= 73 && temp >= 70) {
                            tv4.setText("평균");
                        } else if (temp <= 79 && temp >= 74) {
                            tv4.setText("평균 이하");
                        } else if (temp >= 80) {
                            tv4.setText("나쁨");
                        }
                    }
                } else if (s2 == 2) {
                    if (age2 <= 25 && age2 >= 18) {
                        if (temp <= 60 && temp >= 54) {
                            tv4.setText("매우건강");
                        } else if (temp <= 73 && temp >= 61) {
                            tv4.setText("건강");
                        } else if (temp <= 78 && temp >= 74) {
                            tv4.setText("평균");
                        } else if (temp <= 84 && temp >= 79) {
                            tv4.setText("평균 이하");
                        } else if (temp >= 85) {
                            tv4.setText("나쁨");
                        }
                    } else if (age2 <= 35 && age2 >= 26) {
                        if (temp <= 59 && temp >= 54) {
                            tv4.setText("매우건강");
                        } else if (temp <= 72 && temp >= 60) {
                            tv4.setText("건강");
                        } else if (temp <= 76 && temp >= 73) {
                            tv4.setText("평균");
                        } else if (temp <= 82 && temp >= 77) {
                            tv4.setText("평균 이하");
                        } else if (temp >= 83) {
                            tv4.setText("나쁨");
                        }
                    } else if (age2 <= 45 && age2 >= 36) {
                        if (temp <= 59 && temp >= 54) {
                            tv4.setText("매우건강");
                        } else if (temp <= 773 && temp >= 60) {
                            tv4.setText("건강");
                        } else if (temp <= 78 && temp >= 74) {
                            tv4.setText("평균");
                        } else if (temp <= 84 && temp >= 79) {
                            tv4.setText("평균 이하");
                        } else if (temp >= 85) {
                            tv4.setText("나쁨");
                        }
                    } else if (age2 <= 55 && age2 >= 46) {
                        if (temp <= 60 && temp >= 54) {
                            tv4.setText("매우건강");
                        } else if (temp <= 73 && temp >= 61) {
                            tv4.setText("건강");
                        } else if (temp <= 77 && temp >= 74) {
                            tv4.setText("평균");
                        } else if (temp <= 83 && temp >= 78) {
                            tv4.setText("평균 이하");
                        } else if (temp >= 84) {
                            tv4.setText("나쁨");
                        }
                    } else if (age2 <= 65 && age2 >= 56) {
                        if (temp <= 59 && temp >= 54) {
                            tv4.setText("매우건강");
                        } else if (temp <= 73 && temp >= 60) {
                            tv4.setText("건강");
                        } else if (temp <= 77 && temp >= 74) {
                            tv4.setText("평균");
                        } else if (temp <= 83 && temp >= 78) {
                            tv4.setText("평균 이하");
                        } else if (temp >= 84) {
                            tv4.setText("나쁨");
                        }
                    } else if (age2 >= 66) {
                        if (temp <= 59 && temp >= 54) {
                            tv4.setText("매우건강");
                        } else if (temp <= 72 && temp >= 60) {
                            tv4.setText("건강");
                        } else if (temp <= 76 && temp >= 73) {
                            tv4.setText("평균");
                        } else if (temp <= 84 && temp >= 77) {
                            tv4.setText("평균 이하");
                        } else if (temp >= 85) {
                            tv4.setText("나쁨");
                        }
                    }

                }
            }
        });

        btnInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bpm = Integer.parseInt(et1.getText().toString());
                heartbeat.setDurationBasedOnBPM(bpm);
                bpm_buf.add(bpm);
                tv3.setText("" + bpm + " bpm");
                int temp = 0;
                for (int i = 0; i < bpm_buf.size(); i++) {
                    temp += bpm_buf.get(i);
                }
                temp = temp / bpm_buf.size();
                tv5.setText("" + temp + " bpm");

                if (s2 == 1) {
                    if (age2 <= 25 && age2 >= 18) {
                        if (temp <= 55 && temp >= 49) {
                            tv4.setText("매우건강");
                        } else if (temp <= 69 && temp >= 56) {
                            tv4.setText("건강");
                        } else if (temp <= 73 && temp >= 70) {
                            tv4.setText("평균");
                        } else if (temp <= 81 && temp >= 74) {
                            tv4.setText("평균 이하");
                        } else if (temp >= 82) {
                            tv4.setText("나쁨");
                        }
                    } else if (age2 <= 35 && age2 >= 26) {
                        if (temp <= 54 && temp >= 49) {
                            tv4.setText("매우건강");
                        } else if (temp <= 70 && temp >= 55) {
                            tv4.setText("건강");
                        } else if (temp <= 74 && temp >= 71) {
                            tv4.setText("평균");
                        } else if (temp <= 81 && temp >= 75) {
                            tv4.setText("평균 이하");
                        } else if (temp >= 82) {
                            tv4.setText("나쁨");
                        }
                    } else if (age2 <= 45 && age2 >= 36) {
                        if (temp <= 56 && temp >= 50) {
                            tv4.setText("매우건강");
                        } else if (temp <= 70 && temp >= 55) {
                            tv4.setText("건강");
                        } else if (temp <= 75 && temp >= 71) {
                            tv4.setText("평균");
                        } else if (temp <= 82 && temp >= 75) {
                            tv4.setText("평균 이하");
                        } else if (temp >= 83) {
                            tv4.setText("나쁨");
                        }
                    } else if (age2 <= 55 && age2 >= 46) {
                        if (temp <= 57 && temp >= 50) {
                            tv4.setText("매우건강");
                        } else if (temp <= 71 && temp >= 58) {
                            tv4.setText("건강");
                        } else if (temp <= 76 && temp >= 72) {
                            tv4.setText("평균");
                        } else if (temp <= 83 && temp >= 77) {
                            tv4.setText("평균 이하");
                        } else if (temp >= 84) {
                            tv4.setText("나쁨");
                        }
                    } else if (age2 <= 65 && age2 >= 56) {
                        if (temp <= 56 && temp >= 51) {
                            tv4.setText("매우건강");
                        } else if (temp <= 71 && temp >= 57) {
                            tv4.setText("건강");
                        } else if (temp <= 75 && temp >= 72) {
                            tv4.setText("평균");
                        } else if (temp <= 81 && temp >= 76) {
                            tv4.setText("평균 이하");
                        } else if (temp >= 82) {
                            tv4.setText("나쁨");
                        }
                    } else if (age2 >= 66) {
                        if (temp <= 55 && temp >= 50) {
                            tv4.setText("매우건강");
                        } else if (temp <= 69 && temp >= 56) {
                            tv4.setText("건강");
                        } else if (temp <= 73 && temp >= 70) {
                            tv4.setText("평균");
                        } else if (temp <= 79 && temp >= 74) {
                            tv4.setText("평균 이하");
                        } else if (temp >= 80) {
                            tv4.setText("나쁨");
                        }
                    }
                } else if (s2 == 2) {
                    if (age2 <= 25 && age2 >= 18) {
                        if (temp <= 60 && temp >= 54) {
                            tv4.setText("매우건강");
                        } else if (temp <= 73 && temp >= 61) {
                            tv4.setText("건강");
                        } else if (temp <= 78 && temp >= 74) {
                            tv4.setText("평균");
                        } else if (temp <= 84 && temp >= 79) {
                            tv4.setText("평균 이하");
                        } else if (temp >= 85) {
                            tv4.setText("나쁨");
                        }
                    } else if (age2 <= 35 && age2 >= 26) {
                        if (temp <= 59 && temp >= 54) {
                            tv4.setText("매우건강");
                        } else if (temp <= 72 && temp >= 60) {
                            tv4.setText("건강");
                        } else if (temp <= 76 && temp >= 73) {
                            tv4.setText("평균");
                        } else if (temp <= 82 && temp >= 77) {
                            tv4.setText("평균 이하");
                        } else if (temp >= 83) {
                            tv4.setText("나쁨");
                        }
                    } else if (age2 <= 45 && age2 >= 36) {
                        if (temp <= 59 && temp >= 54) {
                            tv4.setText("매우건강");
                        } else if (temp <= 773 && temp >= 60) {
                            tv4.setText("건강");
                        } else if (temp <= 78 && temp >= 74) {
                            tv4.setText("평균");
                        } else if (temp <= 84 && temp >= 79) {
                            tv4.setText("평균 이하");
                        } else if (temp >= 85) {
                            tv4.setText("나쁨");
                        }
                    } else if (age2 <= 55 && age2 >= 46) {
                        if (temp <= 60 && temp >= 54) {
                            tv4.setText("매우건강");
                        } else if (temp <= 73 && temp >= 61) {
                            tv4.setText("건강");
                        } else if (temp <= 77 && temp >= 74) {
                            tv4.setText("평균");
                        } else if (temp <= 83 && temp >= 78) {
                            tv4.setText("평균 이하");
                        } else if (temp >= 84) {
                            tv4.setText("나쁨");
                        }
                    } else if (age2 <= 65 && age2 >= 56) {
                        if (temp <= 59 && temp >= 54) {
                            tv4.setText("매우건강");
                        } else if (temp <= 73 && temp >= 60) {
                            tv4.setText("건강");
                        } else if (temp <= 77 && temp >= 74) {
                            tv4.setText("평균");
                        } else if (temp <= 83 && temp >= 78) {
                            tv4.setText("평균 이하");
                        } else if (temp >= 84) {
                            tv4.setText("나쁨");
                        }
                    } else if (age2 >= 66) {
                        if (temp <= 59 && temp >= 54) {
                            tv4.setText("매우건강");
                        } else if (temp <= 72 && temp >= 60) {
                            tv4.setText("건강");
                        } else if (temp <= 76 && temp >= 73) {
                            tv4.setText("평균");
                        } else if (temp <= 84 && temp >= 77) {
                            tv4.setText("평균 이하");
                        } else if (temp >= 85) {
                            tv4.setText("나쁨");
                        }
                    }

                }
            }
        });

    }

}
