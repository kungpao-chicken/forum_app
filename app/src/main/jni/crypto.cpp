//#include <jni.h>
#include <string>
#include <android/log.h>
#include "MD5.h"
using namespace std;
extern "C" JNIEXPORT jstring JNICALL
Java_com_example_wangzhixiang_librarytest_utils_Utils_getAppVerify(JNIEnv *env, jobject instance,
                                                        jobject hashMapInfo) {
// 用于拼接字符串的数组
    char buff[1000] = {0};
    // 用于拼接字符串的“游标”指针
    char *pos = buff;
    // TODO

// 获取HashMap类entrySet()方法ID
    jclass hashmapClass = env->FindClass("java/util/HashMap");
    jmethodID entrySetMID = env->GetMethodID(hashmapClass, "entrySet", "()Ljava/util/Set;");
    // 调用entrySet()方法获取Set对象
    jobject setObj = env->CallObjectMethod(hashMapInfo, entrySetMID);

    // 获取Set类中iterator()方法ID
    jclass setClass = env->FindClass("java/util/Set");
    jmethodID iteratorMID = env->GetMethodID(setClass, "iterator", "()Ljava/util/Iterator;");
    // 调用iterator()方法获取Iterator对象
    jobject iteratorObj = env->CallObjectMethod(setObj, iteratorMID);

    // 获取Iterator类中hasNext()方法ID
    // 用于while循环判断HashMap中是否还有数据
    jclass iteratorClass = env->FindClass("java/util/Iterator");
    jmethodID hasNextMID = env->GetMethodID(iteratorClass, "hasNext", "()Z");
    // 获取Iterator类中next()方法ID
    // 用于读取HashMap中的每一条数据
    jmethodID nextMID = env->GetMethodID(iteratorClass, "next", "()Ljava/lang/Object;");

    // 获取Map.Entry类中getKey()和getValue()的方法ID
    // 用于读取键值对，注意：内部类使用$符号表示
    jclass entryClass = env->FindClass("java/util/Map$Entry");
    jmethodID getKeyMID = env->GetMethodID(entryClass, "getKey", "()Ljava/lang/Object;");
    jmethodID getValueMID = env->GetMethodID(entryClass, "getValue", "()Ljava/lang/Object;");

    // HashMap只能存放引用数据类型，不能存放int等基本数据类型
    // 使用Integer类的intValue()方法获取int数据
    jclass integerClass = env->FindClass("java/lang/Integer");
    jmethodID valueMID = env->GetMethodID(integerClass, "intValue", "()I");
    sprintf(pos, "%s", "{");
    pos += 1;
    // 循环检测HashMap中是否还有数据
    while (env->CallBooleanMethod(iteratorObj, hasNextMID)) {
        // 读取一条数据
        jobject entryObj = env->CallObjectMethod(iteratorObj, nextMID);

        // 提取数据中key值：String类型课程名字
        jstring paramKeyJS = (jstring) env->CallObjectMethod(entryObj, getKeyMID);
        if (paramKeyJS == NULL)   // HashMap允许null类型
            continue;
        // jstring转C风格字符串
        const char *keyStr = env->GetStringUTFChars(paramKeyJS, NULL);
        jstring paramValueObj = (jstring )env->CallObjectMethod(entryObj, getValueMID);
        if (paramValueObj == NULL)
            continue;
        const char *valueStr = env->GetStringUTFChars(paramValueObj, NULL);

        // 拼接字符串，sprintf函数返回拼接字符个数
        int keyLen = sprintf(pos, "\"%s\":", keyStr);
        pos += keyLen;
        int valueLen = sprintf(pos, "\"%s\",", valueStr);
        pos += valueLen;

        // 释放UTF字符串资源
        env->ReleaseStringUTFChars(paramKeyJS, keyStr);
        // 释放JNI局部引用资源
        env->DeleteLocalRef(entryObj);
        env->DeleteLocalRef(paramKeyJS);
        env->DeleteLocalRef(paramValueObj);
    }
    *(pos-1) = 125;
    *(pos) = 0;
    int i;
    for (i = 0; i < strlen(buff); ++i) {
        buff[i] = (((int)buff[i]) + 32) % 128;
    }
    MD5 md5 = MD5(buff);
    string m = md5.update(env, buff);
    string result = md5.hexdigest();
    // 释放JNI局部引用: jclass jobject
    env->DeleteLocalRef(hashmapClass);
    env->DeleteLocalRef(setObj);
    env->DeleteLocalRef(setClass);
    env->DeleteLocalRef(iteratorObj);
    env->DeleteLocalRef(iteratorClass);
    env->DeleteLocalRef(entryClass);
    env->DeleteLocalRef(integerClass);
    return env->NewStringUTF(m.c_str());
}