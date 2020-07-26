package com.example.cleanarch.core.utils

import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Point
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.cleanarch.R
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

fun Context.getWindowManager() = getSystemService(Context.WINDOW_SERVICE) as WindowManager

fun Context.getDisplaySize() = Point().apply {
    getWindowManager().defaultDisplay.getSize(this)
}

fun Context.displayWidth(): Int = getDisplaySize().x

fun Context.displayHeight(): Int = getDisplaySize().y

fun Context.dp(dp: Float): Int = (dp * resources.displayMetrics.density).toInt()

fun Context.sp(sp: Float): Int =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, resources.displayMetrics).toInt();

fun Context.inflate(@LayoutRes layout: Int): View =
    LayoutInflater.from(this).inflate(layout, null, false)

fun Context.inflate(@LayoutRes layout: Int, root: ViewGroup, attachedToRoot: Boolean): View =
    LayoutInflater.from(this).inflate(layout, root, attachedToRoot)

fun Context.easyToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.easyToast(message: Int) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.collapse() {
    AnimationUtils.collapse(this)
}

fun View.expand() {
    AnimationUtils.expand(this)
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun EditText.getString(): String {
    return this.text.toString()
}

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

fun FragmentActivity.hideKeyboard() {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(window.decorView.windowToken, 0)
}

fun FragmentActivity.navigateTo(screen: Class<out Activity>) {
    startActivity(Intent(this, screen))
}

fun FragmentActivity.navigateAndFinish(screen: Class<out Activity>) {
    startActivity(Intent(this, screen))
    finish()
}


fun Fragment.hideKeyboard() {
    if (context is AppCompatActivity) {
        (context as AppCompatActivity).hideKeyboard()
    }
}

fun Fragment.navigateTo(screen: Class<out Activity>) {
    startActivity(Intent(context, screen))
}

fun Fragment.navigateTo(bundle: Bundle, screen: Class<out Activity>) {
    startActivity(Intent(context, screen).putExtra("data", bundle))
}

fun Fragment.navigateAndFinish(screen: Class<out Activity>) {
    startActivity(Intent(context, screen))
    (context as Activity).finish()
}

fun String.isValidEmail(): Boolean {
    val pattern =
        Pattern.compile("(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
    return pattern.matcher(this).matches()
}

fun String.isValidMobileNumber(): Boolean {
    val pattern =
        Pattern.compile("^\\+[0-9]{10,13}\$")
    return pattern.matcher(this).matches()
}

fun String.isValidPassword(): Boolean {
    return length > 5
}

fun String.isValidName(): Boolean {
    return length > 2
}

fun EditText.isValidEmail(): Boolean {
    return if (!text.toString().isValidEmail()) {
        error = "Valid email"
        false
    } else
        true
}

@SuppressLint("SimpleDateFormat")
fun Context.getDate(milliSeconds: Long, dateFormat: String): String {
    val formatter = SimpleDateFormat(dateFormat)
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = milliSeconds
    return formatter.format(calendar.getTime())
}

fun ChipGroup.setList(activity: Activity, @LayoutRes chipId: Int, list: List<String>) {
    if (!list.isNullOrEmpty()) {
        list.forEach {
            val chip = activity.layoutInflater.inflate(chipId, null, false) as Chip
            chip.text = it
            this.addView(chip)
        }
    }
}


inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
    val editor = this.edit()
    operation(editor)
    editor.apply()
}


fun SharedPreferences.setValue(key: String, value: Any?) {
    when (value) {
        is String? -> edit { it.putString(key, value) }
        is Int -> edit { it.putInt(key, value) }
        is Boolean -> edit { it.putBoolean(key, value) }
        is Float -> edit { it.putFloat(key, value) }
        is Long -> edit { it.putLong(key, value) }
        else -> throw UnsupportedOperationException("Not yet implemented")
    }
}

inline fun <reified T : Any> SharedPreferences.get(key: String, defaultValue: T? = null): T? {
    return when (T::class) {
        String::class -> getString(key, defaultValue as? String) as T?
        Int::class -> getInt(key, defaultValue as? Int ?: -1) as T?
        Boolean::class -> getBoolean(key, defaultValue as? Boolean ?: false) as T?
        Float::class -> getFloat(key, defaultValue as? Float ?: -1f) as T?
        Long::class -> getLong(key, defaultValue as? Long ?: -1) as T?
        else -> throw UnsupportedOperationException("Not yet implemented")
    }
}




