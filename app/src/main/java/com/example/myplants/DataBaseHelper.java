package com.example.myplants;


import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.net.IDN;

import static com.example.myplants.R.*;


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
        System.out.println("here");
        //db=getWritableDatabase();
        //onCreate(db);
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
        String[] favouitesrArray = fContext.getResources().getStringArray(array.favourites);
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


            /*nsertPlant(db, " Aloe Vera", " Indirect or direct sunlight", "Allow soil to dry between watering",
                    "One of aloe’s most famous uses is to soothe sunburnt skin, and it can be also used for cold sores", R.drawable.image_alow_vera);
            insertPlant(db, "English Ivy", "flourishes in bright, indirect light. But can grow anywhere", "Allow soil to dry between watering",
                    "here are 12-15 species of ivy.", R.drawable.image_englishivy);
            insertPlant(db, "spider Plant", "Spider plants prefer bright light for maximum growth but will still flourish in indirect and fairly low amounts of light",
                    "Regularly, but not so much water so that the soil it soggy (the roots can rot this way",
                    "Spider plants also go by the names “airplane plant,” “ribbon plant,” and “spider ivy", R.drawable.image_spider_plant);
            insertPlant(db, "Snake Plant", "Indirect or bright sunlight", "Water rarely and make sure the dirt has fully dried between waterings",
                    "Snake plants are effective at cleaning the air, removing toxins such as formaldehyde", R.drawable.image_snake_plant);
            insertPlant(db, "Calathea", "Bright, indirect light", "Keep the soil moist but not soggy. Use distilled, spring, or rain water when possible",
                    ">On some varieties of Calathea it looks like there are eyes on the leaves, just like peacock feathers. That is why Calathea is also referred to as the Peacock Plant",
                    R.drawable.image_calathea);
            insertPlant(db, "Palm Tree", "Bright indirect light to bright shade.", "Water really well in the growing season little over winter. never allow the pot to stand in water",
                    "Over 2500 species of palm trees exist today", R.drawable.image_palm);
            insertPlant(db, "Chinese money plant", "Bright, indirect sunlight. Rotate to obtain symmetry", "Once a week",
                    "Place a coin in the soil with your Chinese money plant, and it’ll spontaneously start to attract wealth.", R.drawable.image_pilea);
            insertPlant(db, "Ficus", "Indirect sunlight", "water when the plants soil is dry a few inches down", "he name of the plant originated from Latin “Ficus” that stands for “fig.", R.drawable.image_ficus);
            insertPlant(db, "Calamondin Orange Tree", "direct sunlight. More sun the better", "Water thoroughly, allowing the surface of the soil to dry between watering", "Calamondin oranges are a dwarf citrus variety", R.drawable.image_calamondin);
            insertPlant(db, "Jade Plant", "Direct Sunlight", "Allow soil to dry between waterings", "Tortoises like to eat leaves of the jade plant", R.drawable.image_jade);
            insertPlant(db, "Alocasia Zebrina", "Bright-light spot with indirect sunlight", "Water about twice per week in the spring and reduce to about once per week in the winter", "The plant is also known as Elephant\\'s Ear.", drawable.image_zebrina);
            insertPlant(db, "Guzmania", "Bright, natural light. Avoid exposure to any direct, hot sun", "Water when the potting soil is dry to the touch and in the cup of the plant",
                    "Guzmania dies after it has produced its flowers in summer but new plants can easily be propagated from the offsets which appear as the parent plant dies", drawable.image_guzmania);

*/
//

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
            //db=this.getWritableDatabase();
            ContentValues Plantvalues = new ContentValues();
            Plantvalues.put("Plant_information", plantinformation);
            Plantvalues.put("Light_requirements", lightRequirment);
            Plantvalues.put("Water_requirements", waterRequirment);
            Plantvalues.put("Fun_fact", funfacts);
            Plantvalues.put("Image_Resources_ID", resourceID);
            //db.insert("Plant", null, Plantvalues);
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