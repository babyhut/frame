package com.frame.project.annotate;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.view.View;

import java.lang.reflect.Field;

/**
 * Created by xuyanyun on 2017/5/9.
 */

public class AnnotateUtil {
    public AnnotateUtil() {
    }

    public static void initBindView(Object currentClass, View sourceView, View childView) {
        Field[] fields = currentClass.getClass().getDeclaredFields();
        if(fields != null && fields.length > 0) {
            Field[] var7 = fields;
            int var6 = fields.length;

            for(int var5 = 0; var5 < var6; ++var5) {
                Field field = var7[var5];
                BindView bindView = (BindView)field.getAnnotation(BindView.class);
                if(bindView != null) {
                    int viewId = bindView.id();
                    boolean clickLis = bindView.click();
                    boolean changeLis = bindView.change();
                    boolean focusLis = bindView.focus();
                    boolean child = bindView.child();

                    try {
                        field.setAccessible(true);
                        View e = null;
                        if(child && childView != null) {
                            e = childView.findViewById(viewId);
                        } else if(!child && sourceView != null) {
                            e = sourceView.findViewById(viewId);
                        }

                        if(clickLis && e != null) {
                            e.setOnClickListener((View.OnClickListener)currentClass);
                        }

                        if(e != null) {
                            field.set(currentClass, e);
                        }
                    } catch (Exception var15) {
                        var15.printStackTrace();
                    }
                }
            }
        }

    }

    public static void initBindView(Activity aty) {
        initBindView(aty, aty.getWindow().getDecorView(), (View)null);
    }

    public static void initBindView(View view) {
        Context cxt = view.getContext();
        if(cxt instanceof Activity) {
            initBindView((Activity)cxt, (View)null, view);
        } else {
            throw new RuntimeException("view must into Activity");
        }
    }

    public static void initBindView(Fragment frag) {
        initBindView(frag, frag.getActivity().getWindow().getDecorView(), (View)null);
    }
}
