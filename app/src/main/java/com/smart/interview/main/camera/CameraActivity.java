/*
 * Copyright 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.smart.interview.main.camera;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.smart.interview.R;

public class CameraActivity extends Activity {
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        if (null == savedInstanceState) {
            initSelectImageTypeDialog();
        }
    }

    private int imageType;

    private void initSelectImageTypeDialog() {
        AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.dialog_image_type_title))
        .setMessage(getResources().getString(R.string.dialog_image_type_message))
        .setPositiveButton(getResources().getString(R.string.dialog_image_type_jpg), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                imageType = Camera2BasicFragment.IMAGE_TYPE_JPG;
                dialog.dismiss();
            }
        }).setNegativeButton(getResources().getString(R.string.dialog_image_type_yuv), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                imageType = Camera2BasicFragment.IMAGE_TYPE_YUV;
                dialog.dismiss();
            }
        }).setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                getFragmentManager().beginTransaction()
                .replace(R.id.container, Camera2BasicFragment.newInstance(imageType))
                .commit();
            }
        });
        dialog = builder.create();
        dialog.show();
    }

}
