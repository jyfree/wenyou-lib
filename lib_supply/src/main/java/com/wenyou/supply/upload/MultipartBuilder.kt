package com.wenyou.supply.upload

import okhttp3.MultipartBody
import java.io.File


object MultipartBuilder {

    fun filesToMultipartBody(
        file: File,
        uploadProgressListener: UploadProgressListener? = null,
        isReckonSpeed: Boolean = false
    ): MultipartBody {
        val builder = MultipartBody.Builder()
        val uploadFileRequestBody =
            UploadFileRequestBody(
                file,
                uploadProgressListener,
                isReckonSpeed
            )
        builder.addFormDataPart("file", file.name, uploadFileRequestBody)
        builder.setType(MultipartBody.FORM)
        return builder.build()
    }

    fun filesToMultipartBody(
        files: List<File>,
        uploadProgressListener: UploadProgressListener? = null,
        isReckonSpeed: Boolean = false
    ): MultipartBody {
        var totalLength: Long = 0
        files.forEach {
            totalLength += it.length()
        }
        uploadProgressListener?.onTotalLength(totalLength)
        val builder = MultipartBody.Builder()
        files.forEach {
            val uploadFileRequestBody =
                UploadFileRequestBody(
                    it,
                    uploadProgressListener,
                    isReckonSpeed
                )
            builder.addFormDataPart("files", it.name, uploadFileRequestBody)
        }
        builder.setType(MultipartBody.FORM)
        return builder.build()
    }
}