# Survivo (Android MVP)

هذا مشروع مبدئي لتطبيق أندرويد يعرض متاجر على خريطة OpenStreetMap ويظهر موقع المستخدم (GPS).  
المشروع يستخدم osmdroid للخريطة وPlay Services للحصول على الموقع.

تشغيل سريع:
1. افتح Android Studio → New Project → Empty Activity.
2. استبدل ملفات `app/build.gradle`, `AndroidManifest.xml`, وملفات `MainActivity`, `activity_main.xml`, `Shop.kt` بالمحتوى أعلاه.
3. امنح الإذن ACCESS_FINE_LOCATION عند تشغيل التطبيق.
4. سيظهر خريطة ومجموعة متاجر demo. انقر على أي دبوس لتحريك الكاميرا له.

الخطوات التالية لتطوير التطبيق الواقعي:
- ربط قاعدة بيانات (Firestore أو REST API) لجلب المتاجر والمنتجات الحقيقية.
- شاشة تفاصيل متجر + قائمة منتجات + سلة + نظام حجز.
- واجهة تسجيل/تسجيل دخول (Firebase Auth أو نظام خاص).
- تمكين إشعارات (Firebase Cloud Messaging) لتأكيد الحجز/حالة الطلب.
- (اختياري) لوحة إدارة للمتاجر لإضافة/تعديل المنتجات والمواعيد.

اقتراحات للـ Backend (سهل الإعداد):
- Firebase (Auth, Firestore, Storage) — مناسب لـ MVP وسهل الدمج.
- لاحقاً يمكن الانتقال إلى خادم Node/Express + PostgreSQL إذا احتجت مرونة أكبر.

التكلفة:
- osmdroid + OpenStreetMap مجاني للمستخدمين؛ تكاليف قد تظهر إذا استخدمت خدمات مدفوعة (مثل Mapbox/Google Maps أو استضافة كثيفة للـ Backend).