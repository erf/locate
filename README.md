locate
======

A minimal Android library for one-shot location request

**Supported Android versions**: Android 7+

# Usage
```java
new Locate(this).request(new Locate.Handler() {
    @Override
    public void found(Location location) {
        Log.d("location", location.toString());
    }
});
```

# Install
```groovy
compile 'com.apptakk.locate:locate:0.1.0'
```

