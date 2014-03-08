package com.linchproject.framework;

import com.linchproject.core.Params;
import com.linchproject.core.Result;
import com.linchproject.framework.assets.AssetService;

import java.io.InputStream;

/**
 * @author Georg Schmidl
 */
public abstract class AssetsController extends com.linchproject.core.Controller {

    protected AssetService assetService;

    public Result _(Params params) {
        InputStream inputStream = assetService.getInputStream(route.getAfterController());
        return inputStream != null? binary(inputStream): error("");
    }

    public void setAssetService(AssetService assetService) {
        this.assetService = assetService;
    }
}
