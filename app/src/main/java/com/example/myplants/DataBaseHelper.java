package com.example.myplants;


import android.content.ContentValues;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.myplants.R.array;


public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String Plant = "plant";
    public static final String Table_name = "plant_table";
    public static final String col_ID = "ID ";
    public static final String col_plantinformation = "Plant_information";
    public static final String clol_lightRequriment = "Light_requirements";
    public static final String Col_WaterRequriment = "Water_requirements";
    public static final String col_Fun_fact = " Fun_fact";
    public static final String col_Image_Resources_ID = " Image_Resources_ID";
    //SQLiteDatabase db;


    Context fContext;

    public DataBaseHelper(Context context) {
        super(context, Plant, null, 1);
        fContext = context;


    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("not created yet!!");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + Plant + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, Plant_information TEXT, Light_requirements TEXT, Water_requirements TEXT, Fun_fact TEXT, Image_Resources_ID INTEGER)"
        );
        System.out.println("created");

        String[] plantArray = fContext.getResources().getStringArray(array.plants);
        String[] lightArray = fContext.getResources().getStringArray(array.lightRequirements);
        String[] waterArray = fContext.getResources().getStringArray(array.waterRequirements);
        String[] funArray = fContext.getResources().getStringArray(array.funFacts);
        TypedArray imagesArray = fContext.getResources().obtainTypedArray(array.plantimages);

        int len = plantArray.length;

        for (int i = 0; i < len; i++) {
            if (insertPlant(db, plantArray[i], lightArray[ i], waterArray[i], funArray[i], imagesArray.getResourceId(i, 0))){
                System.out.println("True");
            }else{
                System.out.println("False");
            }
        }




        }


        @Override
        public void onUpgrade (SQLiteDatabase db,int oldVersion, int newVersion){
            db.execSQL("DROP TABLE IF EXISTS " + Plant);
            onCreate(db);

        }

        private boolean insertPlant (SQLiteDatabase db, String plantinformation, String
        lightRequirment,
                String waterRequirment, String funfacts,int resourceID)

        {

            ContentValues Plantvalues = new ContentValues();
            Plantvalues.put("Plant_information", plantinformation);
            Plantvalues.put("Light_requirements", lightRequirment);
            Plantvalues.put("Water_requirements", waterRequirment);
            Plantvalues.put("Fun_fact", funfacts);
            Plantvalues.put("Image_Resources_ID", resourceID);

            long result = db.insert(Plant, null, Plantvalues);
            if (result == -1)
                return false;
            else

                return true;
        }

        public Cursor getFilterData () {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor res = db.rawQuery("SELECT * FROM Plant;", null);
                    /*db.query("Plant", new String[]{"Plant_information", "Light_requirements", "Water_requirements",
                            " Fun_fact", " Image_Resources_ID"}, "ID = ?", new String[]{Integer.toString(4)},
                    null, null, null);*/

            return res;


        }
    }