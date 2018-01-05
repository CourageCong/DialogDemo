package com.fc.dialogdemo;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by fc on 2018/1/3.
 */

public class DFragment extends DialogFragment {

    private static final String DIALOG_KING = "dialog_kind";

    public static final int NORMAL_DIALOG = 0;//普通对话框
    public static final int ITEM_DIALOG = 1;//传统单选列表对话框
    public static final int SINGLE_CHOICE_DIALOG = 2;//永久性单选对话框
    public static final int MULTI_CHOICE_DIALOG = 3;//永久性多选对话框
    public static final int DEFINE_DIALOG = 4;//普通对话框


    /**
     * 通过该方法创建实例
     */
    public static DFragment createDFragment(int kind) {
        Bundle bundle = new Bundle();
        bundle.putInt(DIALOG_KING, kind);
        DFragment dFragment = new DFragment();
        dFragment.setArguments(bundle);
        return dFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        int kind = bundle.getInt(DIALOG_KING);
        return getDialog(kind);
    }

    public Dialog getDialog(int kind) {

        Dialog dialog = null;
        switch (kind) {
            case NORMAL_DIALOG:
                dialog = getNormalDialog();
                break;
            case ITEM_DIALOG:
                dialog = getItemDialog();
                break;
            case SINGLE_CHOICE_DIALOG:
                dialog = getSingleChoiceDialog();
                break;
            case MULTI_CHOICE_DIALOG:
                dialog = getMultiChoiceDialog();
                break;
            case DEFINE_DIALOG:
                dialog = getDefineDialog();
                break;
        }
        return dialog;
    }


    /**
     * 普通对话框
     */
    public Dialog getNormalDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("标题")
                .setMessage("我是具体内容")
                .setPositiveButton("确定按钮", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("取消按钮", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNeutralButton("中性按钮", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setCancelable(false);
        return builder.create();
    }


    /**
     * 传统单选列表对话框
     */
    public Dialog getItemDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("传统单选列表")
                .setItems(new String[]{"小狗", "小兔", "小猫"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which) {
                            case 0:
                                Toast.makeText(DFragment.this.getActivity(), "小狗", Toast.LENGTH_SHORT).show();
                                break;
                            case 1:
                                Toast.makeText(DFragment.this.getActivity(), "小兔", Toast.LENGTH_SHORT).show();
                                break;
                            case 2:
                                Toast.makeText(DFragment.this.getActivity(), "小猫", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });
        return builder.create();
    }


    /**
     * 永久性单选列表对话框
     */
    public Dialog getSingleChoiceDialog() {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("单选列表")
                .setSingleChoiceItems(new String[]{"小狗", "小兔", "小猫"}, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        return builder.create();

    }


    ArrayList<Integer> list = null;

    /**
     * 永久性多选列表对话框
     */
    public Dialog getMultiChoiceDialog() {
        list = new ArrayList();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("多选列表")
                .setMultiChoiceItems(new String[]{"小狗", "小兔", "小猫"}, new boolean[]{true, false, true}, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) {
                            // If the user checked the item, add it to the selected items
                            list.add(which);
                        } else if (list.contains(which)) {//取消确认时判断保存的列表中是否有该position
                            // Else, if the item is already in the array, remove it
//                            list.remove(which);
                            list.remove(Integer.valueOf(which));
                        }
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        return builder.create();

    }

    /**
     * 自定义对话框
     */
    public Dialog getDefineDialog() {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_signin, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view)
                .setTitle("自定义布局")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        return builder.create();

    }


}
