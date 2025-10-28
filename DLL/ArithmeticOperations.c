#include &lt;jni.h&gt;
#include &quot;ArithmeticOperations.h&quot;
JNIEXPORT jint JNICALL Java_ArithmeticOperations_add(JNIEnv *env,
jobject obj, jint a, jint b) {
return a + b;
}
JNIEXPORT jint JNICALL Java_ArithmeticOperations_subtract(JNIEnv *env,
jobject obj, jint a, jint b) {
return a - b;
}
JNIEXPORT jint JNICALL Java_ArithmeticOperations_multiply(JNIEnv *env,
jobject obj, jint a, jint b) {
return a * b;
}
JNIEXPORT jint JNICALL Java_ArithmeticOperations_divide(JNIEnv *env,
jobject obj, jint a, jint b) {
if (b == 0) {
 return 0;
}
return a / b;
}