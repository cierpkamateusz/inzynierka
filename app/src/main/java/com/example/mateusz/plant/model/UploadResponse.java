package com.example.mateusz.plant.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mateusz on 2016-11-06.
 */
public class UploadResponse {
    @SerializedName("file_name")
    String file_name;
    @SerializedName("error")
    String error;
    @SerializedName("message")
    String message;
    @SerializedName("file_path")
    String file_path;

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }
}
