package com.testdai.utils.glide

import android.content.Context
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions

@GlideModule
class GlideModule  : AppGlideModule() {

    companion object {
        private const val IMAGE_CACHE_SIZE = 70L * 1024 * 1024
        private const val IMAGE_FOLDER = "/drive_license_exam/images"
    }

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        builder.setDefaultRequestOptions(
                RequestOptions()
                        .format(DecodeFormat.PREFER_ARGB_8888)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
        )
        val factory = InternalCacheDiskCacheFactory(context, IMAGE_FOLDER, IMAGE_CACHE_SIZE)
        builder.setDiskCache(factory)
    }

    override fun isManifestParsingEnabled(): Boolean = false
}
