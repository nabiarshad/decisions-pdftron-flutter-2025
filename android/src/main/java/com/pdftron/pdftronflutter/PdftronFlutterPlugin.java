package com.pdftron.pdftronflutter;

import android.content.Context;

import androidx.annotation.NonNull;

import com.pdftron.pdftronflutter.factories.DocumentViewFactory;
import com.pdftron.pdftronflutter.helpers.PluginMethodCallHandler;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.platform.PlatformViewRegistry;

/**
 * PdftronFlutterPlugin
 */
public class PdftronFlutterPlugin implements FlutterPlugin, ActivityAware {

    private static final String viewTypeId = "pdftron_flutter/documentview";
    private PlatformViewRegistry mRegistry;
    private BinaryMessenger mMessenger;
    private MethodChannel mMethodChannel;

    public PdftronFlutterPlugin() { }

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding binding) {
        mMessenger = binding.getBinaryMessenger();
        mRegistry = binding.getPlatformViewRegistry();
        mMethodChannel = new MethodChannel(mMessenger, "pdftron_flutter");
        mMethodChannel.setMethodCallHandler(new PluginMethodCallHandler(mMessenger, binding.getApplicationContext()));
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        if (mMethodChannel != null) {
            mMethodChannel.setMethodCallHandler(null);
            mMethodChannel = null;
        }
        mRegistry = null;
        mMessenger = null;
    }

    @Override
    public void onAttachedToActivity(@NonNull ActivityPluginBinding binding) {
        if (mRegistry != null && mMessenger != null) {
            mRegistry.registerViewFactory(viewTypeId, new DocumentViewFactory(mMessenger, binding.getActivity()));
        }
    }

    @Override
    public void onDetachedFromActivityForConfigChanges() { }

    @Override
    public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding binding) {
        if (mRegistry != null && mMessenger != null) {
            mRegistry.registerViewFactory(viewTypeId, new DocumentViewFactory(mMessenger, binding.getActivity()));
        }
    }

    @Override
    public void onDetachedFromActivity() { }
}