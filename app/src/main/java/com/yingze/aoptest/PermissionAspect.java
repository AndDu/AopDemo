package com.yingze.aoptest;

import android.content.Context;
import android.util.Log;

import androidx.fragment.app.Fragment;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class PermissionAspect {

    private static final String TAG = "TimPermissionAspect";

    //定义切入点
    //execution(@要切入的注解的全类名 所有类 所有方法（任何参数）)
    @Pointcut("execution(@com.yingze.aoptest.Permission * *(..)) && @annotation(permission)")
    public void requestPermission(Permission permission) { }

    //定义在切入点要做的操作
    @Around("requestPermission(permission)")
    public void aroundJointPoint(final ProceedingJoinPoint joinPoint, Permission permission) throws Throwable{

        //初始化context
        Context context = null;

        final Object object = joinPoint.getThis();//得到添加注解的那个类对象
        if (joinPoint.getThis() instanceof Context) {
            context = (Context) object;
        } else if (joinPoint.getThis() instanceof Fragment) {
            context = ((Fragment) object).getActivity();
        } else if (joinPoint.getThis() instanceof android.app.Fragment) {
            context = ((android.app.Fragment) object).getActivity();
        } else {
            //如果在其他跟
        }

        if (context == null || permission == null) {
            Log.d(TAG, "aroundJonitPoint error ");
            return;
        }

        final Context finalContext = context;
        PermissionActivity.requestPermission(context, permission.value(), permission.requestCode(), new IPermission() {
            @Override
            public void ganted() {
                try {
                    joinPoint.proceed();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void cancled() {
                PermissionUtils.invokAnnotation(object, PermissionCanceled.class);
            }

            @Override
            public void denied() {
                PermissionUtils.invokAnnotation(object, PermissionDenied.class);
                PermissionUtils.goToMenu(finalContext);
            }
        });

    }


}
