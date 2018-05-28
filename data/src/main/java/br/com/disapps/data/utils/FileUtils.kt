package br.com.disapps.data.utils

import java.io.File

fun deleteFromCache(filePath:String) {
    try {
        deleteDir(File(filePath))
    } catch (e: Exception) { }
}

fun deleteDir(dir: File?): Boolean {
    if (dir != null && dir.isDirectory) {
        val children = dir.list()
        for (i in children.indices) {
            val success = deleteDir(File(dir, children[i]))
            if (!success) {
                return false
            }
        }
        return dir.delete()
    } else return if (dir != null && dir.isFile) {
        dir.delete()
    } else {
        false
    }
}