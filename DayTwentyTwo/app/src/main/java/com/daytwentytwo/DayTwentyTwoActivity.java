package com.daytwentytwo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DayTwentyTwoActivity extends Activity {
	private TextView lblTextViewOne;
	private EditText editText1;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		lblTextViewOne = (TextView) findViewById(R.id.lblTextViewOne);
		lblTextViewOne.setText(R.string.test_one);//

		editText1 = (EditText) findViewById(R.id.editText1);
		editText1.setText(R.string.test_one);

		Button button1 = (Button) findViewById(R.id.button1);
		button1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				lblTextViewOne.setText(editText1.getText());

				try {
					FileOutputStream fos = openFileOutput("DayTwentyTwoFile",
							Context.MODE_APPEND | Context.MODE_WORLD_READABLE);
					fos.write(editText1.getText().toString().getBytes());
					fos.close();

					String storageState = Environment.getExternalStorageState();
					if (storageState.equals(Environment.MEDIA_MOUNTED)) {
						File file = new File(getExternalFilesDir(null),
								"DayTwentyTwoFileTwo");
						FileOutputStream fos2 = new FileOutputStream(file);
						fos2.write(editText1.getText().toString().getBytes());
						fos2.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});

		Button button2 = (Button) findViewById(R.id.button2);
		button2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					BufferedReader inputReader = new BufferedReader(
							new InputStreamReader(
									openFileInput("DayTwentyTwoFile")));
					String inputString;
					StringBuffer stringBuffer = new StringBuffer();
					while ((inputString = inputReader.readLine()) != null) {
						stringBuffer.append(inputString + "\n");
					}
					lblTextViewOne.setText(stringBuffer.toString());
					
					String storageState = Environment.getExternalStorageState();
					if (storageState.equals(Environment.MEDIA_MOUNTED)) {
						File file = new File(getExternalFilesDir(null),
								"DayTwentyTwoFileTwo");
						

						BufferedReader inputReader2 = new BufferedReader(
								new InputStreamReader(new FileInputStream(file)));
						String inputString2;
						StringBuffer stringBuffer2 = new StringBuffer();
						while ((inputString2 = inputReader2.readLine()) != null) {
							stringBuffer2.append(inputString2 + "\n");
						}
						lblTextViewOne.setText(stringBuffer2.toString());
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

}