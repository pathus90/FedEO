package com.model;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
 
public class ParcelableProductEntry implements Parcelable {
    private ProductEntry productEntry;
 
    public ProductEntry getLaptop() {
        return productEntry;
    }
 
    public ParcelableProductEntry(ProductEntry _productEntry) {
        super();
        this.productEntry = _productEntry;
    }
 
    private ParcelableProductEntry(Parcel in) {
    	productEntry = new ProductEntry();
    	productEntry.setId(in.readString());
        productEntry.setTitle(in.readString());;
        productEntry.setBitmapQuicklook((Bitmap) in.readParcelable(Bitmap.class
                .getClassLoader()));
        productEntry.setBitmapThumbnail((Bitmap) in.readParcelable(Bitmap.class
                .getClassLoader()));
    }
    /*
     * you can use hashCode() here.
     */
    @Override
    public int describeContents() {
        return 0;
    }
    /*
     * Actual object Serialization/flattening happens here. You need to
     * individually Parcel each property of your object.
     */
    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(productEntry.getId());
        parcel.writeString(productEntry.getAntenaLookDirection());
        parcel.writeString(productEntry.getEndDate());
        parcel.writeParcelable(productEntry.getBitmapThumbnail(),
                PARCELABLE_WRITE_RETURN_VALUE);
        parcel.writeParcelable(productEntry.getBitmapQuicklook(),
                PARCELABLE_WRITE_RETURN_VALUE);
    }
 
    /*
     * Parcelable interface must also have a static field called CREATOR,
     * which is an object implementing the Parcelable.Creator interface.
     * Used to un-marshal or de-serialize object from Parcel.
     * http://theopentutorials.com/tutorials/android/android-sending-object-from-one-activity-to-another-using-parcelable/
     */
    public static final Parcelable.Creator<ParcelableProductEntry> CREATOR = 
            new Parcelable.Creator<ParcelableProductEntry>() {
        public ParcelableProductEntry createFromParcel(Parcel in) {
            return new ParcelableProductEntry(in);
        }
        public ParcelableProductEntry[] newArray(int size) {
            return new ParcelableProductEntry[size];
        }
    };
}