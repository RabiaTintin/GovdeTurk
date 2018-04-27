import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFileChooser;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Lenovo
 */
public class Govdeleme extends GovdeTurk {
    
public static String dosyaSec() {
        JFileChooser chooser=new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("Bir (.txt) uzantılı dosya seçiniz...");
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int returnVal=chooser.showOpenDialog(null);
        if (returnVal==JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile().getAbsolutePath();
        } 
        else 
        {
            return null;
        }
    }
    
public void metni_cumle_ve_kelimelere_ayir(){
    try {
        File file = new File(dosyaYolu);
        BufferedReader reader;
        reader = new BufferedReader(new FileReader(file));
        String satir = reader.readLine();
        String[] cumleler = null;
        String[] kelimeler = null;
                
        while (satir!=null) 
        {
            satir=satir.replace(". ",".").replace("! ",".").replace("? ",".").replace(": ",".").replace("; ",".").replace("... ",".").replace("!",".").replace("?",".").replace(":",".").replace(";",".").replace("...",".");
            cumleler=satir.split("[.]"); 
            
            for (String cumle : cumleler)
            {
            cumle=cumle.replaceAll("\\p{P}","").toLowerCase();
            kelimeler=cumle.split("\\s");
           
            for (String kelime : kelimeler) 
            {
           
            if (sifat_agac.search(kelime)==true)
            { 
               int ogeno1, ogeno2=1;
               Boolean x=false;
               ogeno1=oge_no_bul(kelime, kelimeler); ogeno1=ogeno1+1;
               for (String oge : kelimeler) 
                {
                ogeno2=oge_no_bul(oge, kelimeler); 
                   if (ogeno1==ogeno2) 
                    {
                      if (fiil_sorgula(oge).equals(false) && fiilimsi_sorgula(oge).equals(false))
                        {
                           govdeyazdir(kelime);etiketyazdir(kelime,"(isim soylu-sıfat)"); x=true; //"Güzel kız geliyor."
                        }
                      else 
                        {
                           govdeyazdir(kelime);etiketyazdir(kelime,"(isim soylu-zarf)"); x=true; //"Bana güzel bakıyor."
                        }
                        
                    }
                   
                }
              if (x.equals(false)) {govdeyazdir(kelime);etiketyazdir(kelime,"(isim soylu-isim)");} //"Bana bakıyor güzel."
            }
            
            else if (zamir_agac.search(kelime)==true)
            { 
             int ogeno1, ogeno2=1;
               Boolean y=false;
               ogeno1=oge_no_bul(kelime, kelimeler); ogeno1=ogeno1+1;
               for (String oge : kelimeler) 
                {
                ogeno2=oge_no_bul(oge, kelimeler); 
                   if (ogeno1==ogeno2) 
                    {
                      if (fiil_sorgula(oge).equals(false) && fiilimsi_sorgula(oge).equals(false))
                        {
                           govdeyazdir(kelime);etiketyazdir(kelime,"(isim soylu-sıfat)"); y=true; //"Şu araba çok hızlıdır."
                        }
                      else 
                        {
                           govdeyazdir(kelime);etiketyazdir(kelime,"(isim soylu-zamir)"); y=true; //"Şu hızlıdır."
                        }
                        
                    }
                   
                }
              if (y.equals(false)) {govdeyazdir(kelime);etiketyazdir(kelime,"(isim soylu-zamir)");} //"Hızlıdır şu." 
            }
            
            else if (baglac_agac.search(kelime)==true)
            { 
              int ogeno1, ogeno2=1;
               Boolean z=false;
               ogeno1=oge_no_bul(kelime, kelimeler); ogeno1=ogeno1+1;
               for (String oge : kelimeler) 
                {
                ogeno2=oge_no_bul(oge, kelimeler); 
                   if (ogeno1==ogeno2) 
                    {
                      if (fiil_sorgula(oge).equals(false) && fiilimsi_sorgula(oge).equals(false))
                        {
                           govdeyazdir(kelime);etiketyazdir(kelime,"(isim soylu-bağlaç)"); z=true; //"Araba ile kamyon geldi."
                        }
                      else 
                        {
                           govdeyazdir(kelime);etiketyazdir(kelime,"(isim soylu-edat)"); z=true; //"Araba ile geldi."
                        }
                        
                    }
                   
                }
              if (z.equals(false)) {govdeyazdir(kelime);etiketyazdir(kelime,"(isim soylu-edat)");} //"Geldi araba ile."
            }
                                     
            else if ((essesli_agac.search(kelime))==true) 
            {
                int fiil_sayac = 0;
                int ekfiil_sayac = 0;
                String essesli_kelime=kelime;
                for (String oge : kelimeler) 
                {
                      if (oge.equals(kelime)!=true) 
                      {
                       if (fiil_sorgula(oge).equals(true)) fiil_sayac=fiil_sayac+1;
                       else if (ekfiil_kontrol(oge).equals(true)) ekfiil_sayac=ekfiil_sayac+1;
                      }
                }
                if (fiil_sayac==0 && ekfiil_sayac==0) fiil_govde_bul(essesli_kelime); else isim_govde_bul(essesli_kelime);
            }
            
            else
            {
              govde_bul(kelime);
            }
            }
            }
          satir = reader.readLine();
        }
      reader.close(); 
    }catch(IOException e) {
    }
    }  

public static Boolean fiil_sorgula(String kelime){
    
    Boolean fiil=false;
    String kelimenin_ilk_hali=kelime;
         cikan_ek_1="";
         if (isim_soylu_agac.search(kelime)==true) fiil=false;
         
         else
         {
         if (fiil_soylu_agac.search(kelime)==true) fiil=true; // yalın haldeki fiil gövdesi
          
         else 
         {
         File fiil_ekler = new File("fiil_ekler.txt");
         String kelimenin_eksiz_hali=ek_cikar(fiil_ekler, kelime); // sadece bir fiile gelen ekler (varsa) çıkarılır
         cikan_ek_1=cikan_ek;
         if (fiil_soylu_agac.search(kelimenin_eksiz_hali)==true) fiil=true; // fiil govdesi
                 
         else 
         {
         String aday_govde_1=fiil_istisna_kontrol_1(kelimenin_eksiz_hali);
         if (aday_govde_1.equals(kelimenin_eksiz_hali)==false) fiil=true;  // fiil govdesi
         
         else 
         {
         String aday_govde_2=fiil_istisna_kontrol_2(kelimenin_eksiz_hali);
         if (aday_govde_2.equals(kelimenin_eksiz_hali)==false) fiil=true;  // fiil govdesi
         
         else 
         {
         String aday_govde_22=fiil_istisna_kontrol_3(kelimenin_eksiz_hali);
         if (aday_govde_22.equals(kelimenin_eksiz_hali)==false) fiil=true;   // fiil govdesi
          
         else
         {
         String aday_govde_3=olumsuz_fiil_kontrol(kelimenin_eksiz_hali); // fiile gelen olumsuzluk ekleri varsa atılıyor.
         if (aday_govde_3.equals(kelimenin_eksiz_hali)==false) fiil=true; 
                    
         else 
         {
         String aday_govde_4=kuralli_bilesik_fiil_ek_cikar(aday_govde_3);
         if (aday_govde_4.equals(aday_govde_3)==false) fiil=true; 
          
         else 
         {
         if (turemis_fiil_kontrol(aday_govde_4)==true) fiil=true; 
         
         else
         { 
         String enyakin_fiil_govde=ortak_ek_kontrol(kelimenin_ilk_hali,kelimenin_eksiz_hali);
         if (enyakin_fiil_govde.equals(kelimenin_ilk_hali)==false && kelimenin_eksiz_hali.equals(kelimenin_ilk_hali)==false) fiil=true; 
         
         else
         {
         String aday_govde_7=fiil_govde_tahmin_2(kelimenin_ilk_hali, aday_govde_4);
         if (aday_govde_7.equals(aday_govde_4)==false) fiil=true; 
         }}}}}}}}}}
return fiil;
}

public static Boolean fiilimsi_sorgula(String kelime){
    
    Boolean fiilimsi=false;
    String kelimenin_ilk_hali=kelime;
    
         String kelimenin_ekzarfsiz_hali=ekzarf_eki_cikar(kelimenin_ilk_hali);
         File ekfiil_ekler1 = new File("ekfiil_ekler.txt");
         String kelimenin_ekfiilsiz_hali=ekfiil_eki_cikar(ekfiil_ekler1,kelimenin_ekzarfsiz_hali);
         kelimenin_ekfiilsiz_hali=ekfiil_kontrol_1(kelimenin_ekfiilsiz_hali);
         if (isim_soylu_agac.search(kelimenin_ekfiilsiz_hali)==true) fiilimsi=false; //(Örn:arkadaş-mış > isim gövde:arkadaş)
         
         else
         {
         String kelimenin_ilgieksiz_hali=ilgi_eki_cikar(kelimenin_ekfiilsiz_hali);
         if (isim_soylu_agac.search(kelimenin_ilgieksiz_hali)==true) fiilimsi=false; //(Örn:bugün-kü > isim gövde:bugün)
                   
         else
         {   
         String kelimenin_durumeksiz_hali=durum_eki_cikar(kelimenin_ilgieksiz_hali);
         kelimenin_durumeksiz_hali=yumusama(kelimenin_durumeksiz_hali);
         kelimenin_durumeksiz_hali=dusme(kelimenin_durumeksiz_hali);
         kelimenin_durumeksiz_hali=durum_ek_kontrol_2(kelimenin_durumeksiz_hali);
         if (isim_soylu_agac.search(kelimenin_durumeksiz_hali)==true) fiilimsi=false; //(Örn:arkadaş-ın kalemi > isim gövde:arkadaş)
                   
         else 
         {
         kelimenin_durumeksiz_hali=durum_ek_kontrol_1(kelimenin_ilgieksiz_hali,kelimenin_durumeksiz_hali);
         String kelimenin_iyelikeksiz_hali=iyelik_eki_cikar(kelimenin_durumeksiz_hali);
         kelimenin_iyelikeksiz_hali=yumusama(kelimenin_iyelikeksiz_hali);
         kelimenin_iyelikeksiz_hali=dusme(kelimenin_iyelikeksiz_hali);
         kelimenin_iyelikeksiz_hali=iyelik_ek_kontrol(kelimenin_durumeksiz_hali,kelimenin_iyelikeksiz_hali);
         kelimenin_iyelikeksiz_hali=iyelik_ek_kontrol_2(kelimenin_durumeksiz_hali,kelimenin_iyelikeksiz_hali);
         kelimenin_iyelikeksiz_hali=iyelik_ek_kontrol_3(kelimenin_iyelikeksiz_hali);
         if (isim_soylu_agac.search(kelimenin_iyelikeksiz_hali)==true) fiilimsi=false; //(Örn:arkadaş-ım > isim gövde:arkadaş)
         
         else 
         {
         String kelimenin_coguleksiz_hali=cogul_eki_cikar(kelimenin_iyelikeksiz_hali);
         if (isim_soylu_agac.search(kelimenin_coguleksiz_hali)==true) fiilimsi=false; //(Örn:arkadaş-lar > isim gövde:arkadaş)
                
         else 
         {
         String kelimenin_sahiplik_yoksunluk_eksiz_hali=sahiplik_yoksunluk_eki_cikar(kelimenin_coguleksiz_hali);
         if (isim_soylu_agac.search(kelimenin_sahiplik_yoksunluk_eksiz_hali)==true) fiilimsi=false; //(Örn:akıl-lı > isim gövde:akıl)

         else 
         {
         if (yapim_eki_kontrol(kelimenin_sahiplik_yoksunluk_eksiz_hali)==true) fiilimsi=false; //isim govde (yapım eki almış ancak sözlükte bulunmayan isim gövdesi tespiti)

         //------------------------------------------------fiilimsiler
         
         else
         {
         String kelimenin_fiilimsi_eksiz_hali=fiilimsi_eki_cikar(kelimenin_sahiplik_yoksunluk_eksiz_hali);
         if (fiil_soylu_agac.search(kelimenin_fiilimsi_eksiz_hali)==true) fiilimsi=true;
         
         else
         {
         String kelimenin_olumsuz_eksiz_hali=olumsuz_fiil_kontrol(kelimenin_fiilimsi_eksiz_hali);
         if (kelimenin_olumsuz_eksiz_hali.equals(kelimenin_fiilimsi_eksiz_hali)==false) fiilimsi=true;
         
         else
         {
         String kelimenin_kuralli_bilesik_eksiz_hali=kuralli_bilesik_fiil_ek_cikar(kelimenin_olumsuz_eksiz_hali);    
         if (kelimenin_kuralli_bilesik_eksiz_hali.equals(kelimenin_olumsuz_eksiz_hali)==false) fiilimsi=true;
         
         else
         {
         if (turemis_fiil_kontrol(kelimenin_kuralli_bilesik_eksiz_hali)==true) fiilimsi=true;
         }}}}}}}}}}
              
return fiilimsi;
}




public void fiil_govde_bul(String kelime) {
    
         String kelimenin_ilk_hali=kelime;
         cikan_ek_1="";
       
         if (fiil_soylu_agac.search(kelime)==true) {govdeyazdir(kelime);etiketyazdir(kelime,"(fiil soylu-yüklem)");} // yalın haldeki fiil gövdesi
          
         else 
         {
         File fiil_ekler = new File("fiil_ekler.txt");
         String kelimenin_eksiz_hali=ek_cikar(fiil_ekler, kelime); // sadece bir fiile gelen ekler (varsa) çıkarılır
         cikan_ek_1=cikan_ek;
         if (fiil_soylu_agac.search(kelimenin_eksiz_hali)==true) {govdeyazdir(kelimenin_eksiz_hali);etiketyazdir(kelimenin_eksiz_hali,"(fiil soylu-yüklem)");} // fiil govdesi
                 
         else 
         {
         String aday_govde_1=fiil_istisna_kontrol_1(kelimenin_eksiz_hali);
         if (aday_govde_1.equals(kelimenin_eksiz_hali)==false) {govdeyazdir(aday_govde_1);etiketyazdir(aday_govde_1,"(fiil soylu-yüklem)*");} // fiil govdesi
         
         else 
         {
         String aday_govde_2=fiil_istisna_kontrol_2(kelimenin_eksiz_hali);
         if (aday_govde_2.equals(kelimenin_eksiz_hali)==false) {govdeyazdir(aday_govde_2);etiketyazdir(aday_govde_2,"(fiil soylu-yüklem)+");} // fiil govdesi
         
         else 
         {
         String aday_govde_22=fiil_istisna_kontrol_3(kelimenin_eksiz_hali);
         if (aday_govde_22.equals(kelimenin_eksiz_hali)==false) {govdeyazdir(aday_govde_22);etiketyazdir(aday_govde_22,"(fiil soylu-yüklem)-");}  // fiil govdesi
          
         else
         {
         String aday_govde_3=olumsuz_fiil_kontrol(kelimenin_eksiz_hali); // fiile gelen olumsuzluk ekleri varsa atılıyor.
         if (aday_govde_3.equals(kelimenin_eksiz_hali)==false) {govdeyazdir(aday_govde_3);etiketyazdir(aday_govde_3,"(fiil soylu-yüklem)?");}
                    
         else 
         {
         String aday_govde_4=kuralli_bilesik_fiil_ek_cikar(aday_govde_3);
         if (aday_govde_4.equals(aday_govde_3)==false) {govdeyazdir(aday_govde_4);etiketyazdir(aday_govde_4,"(fiil soylu-yüklem)/");}
          
         else 
         {
         if (turemis_fiil_kontrol(aday_govde_4)==true) {govdeyazdir(aday_govde_4);etiketyazdir(aday_govde_4,"(fiil soylu-yüklem)!");}
         
         else
         { 
         String enyakin_fiil_govde=ortak_ek_kontrol(kelimenin_ilk_hali,kelimenin_eksiz_hali);
         if (enyakin_fiil_govde.equals(kelimenin_ilk_hali)==false && kelimenin_eksiz_hali.equals(kelimenin_ilk_hali)==false) {govdeyazdir(enyakin_fiil_govde);etiketyazdir(enyakin_fiil_govde,"(fiil soylu-yüklem)%");}
         
         else
         {
         String aday_govde_7=fiil_govde_tahmin_2(kelimenin_ilk_hali, aday_govde_4);
         if (aday_govde_7.equals(aday_govde_4)==false) {govdeyazdir(aday_govde_7); etiketyazdir(aday_govde_7,"(fiil soylu-yüklem)" );}
         
         else
         {
         govdeyazdir(aday_govde_4);etiketyazdir(aday_govde_4,"(fiil soylu-yüklem)#");
         }
         }}}}}}}}}}
                
public void isim_govde_bul(String kelime){
   
  String kelimenin_ilk_hali=kelime;
         cikan_ek_1=""; durum_eki=""; iyelik_eki=""; ekfiil_eki="";
       
         if (isim_soylu_agac.search(kelime)==true) {govdeyazdir(kelime);etiketyazdir(kelime, turbul(kelime));} // yalın haldeki isim gövdesi
        
        //------------------------------------------isim soylu kelimeler (bir ek alanlar)
         
         else
         {               
         String aday_govde1=ekzarf_eki_cikar(kelimenin_ilk_hali);
         if (isim_soylu_agac.search(aday_govde1)==true || yapim_eki_kontrol(aday_govde1)==true) {govdeyazdir(aday_govde1);etiketyazdir(aday_govde1,turbul(aday_govde1));} // arkadaşken > arkadaş
         
         else
         {
         String aday_govde2=sahiplik_yoksunluk_eki_cikar(kelimenin_ilk_hali);
         if (isim_soylu_agac.search(aday_govde2)==true || yapim_eki_kontrol(aday_govde2)==true) {govdeyazdir(aday_govde2);etiketyazdir(aday_govde2,turbul(aday_govde2));} // akılsız > akıl
         
         else
         {
         String aday_govde3=fiilimsi_eki_cikar(kelimenin_ilk_hali);
         if (fiil_soylu_agac.search(aday_govde3)==true) {govdeyazdir(aday_govde3);etiketyazdir(aday_govde3,"(isim soylu-fiilimsi)--");} // geldiği > gel
         
         else
         {
         
         File ekfiil_ekler = new File("ekfiil_ekler.txt");
         String aday_govde4=ekfiil_eki_cikar(ekfiil_ekler,kelimenin_ilk_hali);
         if (isim_soylu_agac.search(aday_govde4)==true || yapim_eki_kontrol(aday_govde4)==true) {govdeyazdir(aday_govde4);etiketyazdir(aday_govde4,turbul(aday_govde4));} // arkadaşmış > arkadaş
         
         else
         {
         String aday_govde5=ilgi_eki_cikar(kelimenin_ilk_hali);
         if (isim_soylu_agac.search(aday_govde5)==true || yapim_eki_kontrol(aday_govde5)==true) {govdeyazdir(aday_govde5);etiketyazdir(aday_govde5,turbul(aday_govde5));} // bugünkü > bugün
         
         else
         {    
         String aday_govde6=durum_eki_cikar(kelimenin_ilk_hali);
         aday_govde6=yumusama(aday_govde6);
         aday_govde6=dusme(aday_govde6);
         aday_govde6=durum_ek_kontrol_3(aday_govde6);
         if (isim_soylu_agac.search(aday_govde6)==true || yapim_eki_kontrol(aday_govde6)==true) {govdeyazdir(aday_govde6);etiketyazdir(aday_govde6,turbul(aday_govde6));} // arkadaşın kalemi > arkadaş
         
         else 
         {
         String aday_govde7=iyelik_eki_cikar(kelimenin_ilk_hali);
         aday_govde7=yumusama(aday_govde7);
         aday_govde7=dusme(aday_govde7);
         if (isim_soylu_agac.search(aday_govde7)==true || yapim_eki_kontrol(aday_govde7)==true) {govdeyazdir(aday_govde7);etiketyazdir(aday_govde7,turbul(aday_govde7));} // arkadaşım > arkadaş
         
         else 
         {
         String aday_govde8=cogul_eki_cikar(kelimenin_ilk_hali);
         if (isim_soylu_agac.search(aday_govde8)==true || yapim_eki_kontrol(aday_govde6)==true) {govdeyazdir(aday_govde8);etiketyazdir(aday_govde8,turbul(aday_govde8));} //arkadaşlar > arkadaş
         
         else 
         {
         if (yapim_eki_kontrol(kelimenin_ilk_hali)==true) {govdeyazdir(kelimenin_ilk_hali);etiketyazdir(kelimenin_ilk_hali,turbul(kelimenin_ilk_hali));} // kitaplık
         
         //------------------------------------------isim soylu kelimeler (birden fazla ek alanlar)
         
         else
         { 
         String kelimenin_ekzarfsiz_hali=ekzarf_eki_cikar(kelimenin_ilk_hali);
         File ekfiil_ekler1 = new File("ekfiil_ekler.txt");
         String kelimenin_ekfiilsiz_hali=ekfiil_eki_cikar(ekfiil_ekler1,kelimenin_ekzarfsiz_hali);
         kelimenin_ekfiilsiz_hali=ekfiil_kontrol_1(kelimenin_ekfiilsiz_hali);
         if (isim_soylu_agac.search(kelimenin_ekfiilsiz_hali)==true || yapim_eki_kontrol(kelimenin_ekfiilsiz_hali)==true) {govdeyazdir(kelimenin_ekfiilsiz_hali);etiketyazdir(kelimenin_ekfiilsiz_hali,turbul(kelimenin_ekfiilsiz_hali));} //(Örn:arkadaş-mış > isim gövde:arkadaş)
         
         else
         {
         String kelimenin_ilgieksiz_hali=ilgi_eki_cikar(kelimenin_ekfiilsiz_hali);
         if (isim_soylu_agac.search(kelimenin_ilgieksiz_hali)==true || yapim_eki_kontrol(kelimenin_ilgieksiz_hali)==true) {govdeyazdir(kelimenin_ilgieksiz_hali);etiketyazdir(kelimenin_ilgieksiz_hali,turbul(kelimenin_ilgieksiz_hali));} //(Örn:bugün-kü > isim gövde:bugün)
                   
         else
         {   
         String kelimenin_durumeksiz_hali=durum_eki_cikar(kelimenin_ilgieksiz_hali);
         kelimenin_durumeksiz_hali=yumusama(kelimenin_durumeksiz_hali);
         kelimenin_durumeksiz_hali=dusme(kelimenin_durumeksiz_hali);
         kelimenin_durumeksiz_hali=durum_ek_kontrol_2(kelimenin_durumeksiz_hali);
         if (isim_soylu_agac.search(kelimenin_durumeksiz_hali)==true || yapim_eki_kontrol(kelimenin_durumeksiz_hali)==true) {govdeyazdir(kelimenin_durumeksiz_hali);etiketyazdir(kelimenin_durumeksiz_hali,turbul(kelimenin_durumeksiz_hali));} //(Örn:arkadaş-ın kalemi > isim gövde:arkadaş)
                   
         else 
         {
         kelimenin_durumeksiz_hali=durum_ek_kontrol_1(kelimenin_ilgieksiz_hali,kelimenin_durumeksiz_hali);
         String kelimenin_iyelikeksiz_hali=iyelik_eki_cikar(kelimenin_durumeksiz_hali);
         kelimenin_iyelikeksiz_hali=yumusama(kelimenin_iyelikeksiz_hali);
         kelimenin_iyelikeksiz_hali=dusme(kelimenin_iyelikeksiz_hali);
         kelimenin_iyelikeksiz_hali=iyelik_ek_kontrol(kelimenin_durumeksiz_hali,kelimenin_iyelikeksiz_hali);
         kelimenin_iyelikeksiz_hali=iyelik_ek_kontrol_2(kelimenin_durumeksiz_hali,kelimenin_iyelikeksiz_hali);
         kelimenin_iyelikeksiz_hali=iyelik_ek_kontrol_3(kelimenin_iyelikeksiz_hali);
         if (isim_soylu_agac.search(kelimenin_iyelikeksiz_hali)==true || yapim_eki_kontrol(kelimenin_iyelikeksiz_hali)==true) {govdeyazdir(kelimenin_iyelikeksiz_hali);etiketyazdir(kelimenin_iyelikeksiz_hali,turbul(kelimenin_iyelikeksiz_hali));} //(Örn:arkadaş-ım > isim gövde:arkadaş)
         
         else 
         {
         String kelimenin_coguleksiz_hali=cogul_eki_cikar(kelimenin_iyelikeksiz_hali);
         if (isim_soylu_agac.search(kelimenin_coguleksiz_hali)==true || yapim_eki_kontrol(kelimenin_coguleksiz_hali)==true) {govdeyazdir(kelimenin_coguleksiz_hali);etiketyazdir(kelimenin_coguleksiz_hali,turbul(kelimenin_coguleksiz_hali));} //(Örn:arkadaş-lar > isim gövde:arkadaş)
                
         else 
         {
         String kelimenin_sahiplik_yoksunluk_eksiz_hali=sahiplik_yoksunluk_eki_cikar(kelimenin_coguleksiz_hali);
         if (isim_soylu_agac.search(kelimenin_sahiplik_yoksunluk_eksiz_hali)==true || yapim_eki_kontrol(kelimenin_ekfiilsiz_hali)==true) {govdeyazdir(kelimenin_sahiplik_yoksunluk_eksiz_hali);etiketyazdir(kelimenin_sahiplik_yoksunluk_eksiz_hali,turbul(kelimenin_sahiplik_yoksunluk_eksiz_hali));} //(Örn:akıl-lı > isim gövde:akıl)

         else 
         {
         if (yapim_eki_kontrol(kelimenin_sahiplik_yoksunluk_eksiz_hali)==true) {govdeyazdir(kelimenin_sahiplik_yoksunluk_eksiz_hali);etiketyazdir(kelimenin_sahiplik_yoksunluk_eksiz_hali,turbul(kelimenin_sahiplik_yoksunluk_eksiz_hali));} //isim govde (yapım eki almış ancak sözlükte bulunmayan isim gövdesi tespiti)

         //------------------------------------------------fiilimsiler
         
         else
         {
         String kelimenin_fiilimsi_eksiz_hali=fiilimsi_eki_cikar(kelimenin_sahiplik_yoksunluk_eksiz_hali);
         if (fiil_soylu_agac.search(kelimenin_fiilimsi_eksiz_hali)==true) {govdeyazdir(kelimenin_fiilimsi_eksiz_hali);etiketyazdir(kelimenin_fiilimsi_eksiz_hali,"(isim soylu-fiilimsi)+1");}
         
         else
         {
         String kelimenin_olumsuz_eksiz_hali=olumsuz_fiil_kontrol(kelimenin_fiilimsi_eksiz_hali);
         if (kelimenin_olumsuz_eksiz_hali.equals(kelimenin_fiilimsi_eksiz_hali)==false) {govdeyazdir(kelimenin_olumsuz_eksiz_hali);etiketyazdir(kelimenin_olumsuz_eksiz_hali,"(isim soylu-fiilimsi)+2");}
         
         else
         {
         String kelimenin_kuralli_bilesik_eksiz_hali=kuralli_bilesik_fiil_ek_cikar(kelimenin_olumsuz_eksiz_hali);    
         if (kelimenin_kuralli_bilesik_eksiz_hali.equals(kelimenin_olumsuz_eksiz_hali)==false) {govdeyazdir(kelimenin_kuralli_bilesik_eksiz_hali);etiketyazdir(kelimenin_kuralli_bilesik_eksiz_hali,"(isim soylu-fiilimsi)+3");}
         
         else
         {
         if (turemis_fiil_kontrol(kelimenin_kuralli_bilesik_eksiz_hali)==true) {govdeyazdir(kelimenin_kuralli_bilesik_eksiz_hali);etiketyazdir(kelimenin_kuralli_bilesik_eksiz_hali,"(isim soylu-fiilimsi)+4");}
         
         else
         {
         String aday_govde_8=isim_govde_tahmin(kelimenin_ilk_hali,kelimenin_kuralli_bilesik_eksiz_hali);
         if (aday_govde_8.equals(kelimenin_kuralli_bilesik_eksiz_hali)==false) {govdeyazdir(aday_govde_8);etiketyazdir(aday_govde_8,turbul(aday_govde_8));}
         
         else
         {
         govdeyazdir(kelimenin_kuralli_bilesik_eksiz_hali);etiketyazdir(kelimenin_kuralli_bilesik_eksiz_hali,"(isim soylu-isim)#");
         }
}}}}}}}}}}}}}}}}}}}}}}


public void govde_bul (String kelime){
         
         String kelimenin_ilk_hali=kelime;
         cikan_ek_1=""; durum_eki=""; iyelik_eki=""; ekfiil_eki="";
       
         if (fiil_soylu_agac.search(kelime)==true) {govdeyazdir(kelime);etiketyazdir(kelime,"(fiil soylu-yüklem)");} // yalın haldeki fiil gövdesi
          
         else 
         { 
         if (isim_soylu_agac.search(kelime)==true) {govdeyazdir(kelime);etiketyazdir(kelime, turbul(kelime)); a=turbul(kelime);} // yalın haldeki isim gövdesi
          
         else 
         {
         File fiil_ekler = new File("fiil_ekler.txt");
         String kelimenin_eksiz_hali=ek_cikar(fiil_ekler, kelime); // sadece bir fiile gelen ekler (varsa) çıkarılır
         cikan_ek_1=cikan_ek;
         if (fiil_soylu_agac.search(kelimenin_eksiz_hali)==true) {govdeyazdir(kelimenin_eksiz_hali);etiketyazdir(kelimenin_eksiz_hali,"(fiil soylu-yüklem)");} // fiil govdesi
                 
         else 
         {
         String aday_govde_1=fiil_istisna_kontrol_1(kelimenin_eksiz_hali);
         if (aday_govde_1.equals(kelimenin_eksiz_hali)==false) {govdeyazdir(aday_govde_1);etiketyazdir(aday_govde_1,"(fiil soylu-yüklem)*");} // fiil govdesi
         
         else 
         {
         String aday_govde_2=fiil_istisna_kontrol_2(kelimenin_eksiz_hali);
         if (aday_govde_2.equals(kelimenin_eksiz_hali)==false) {govdeyazdir(aday_govde_2);etiketyazdir(aday_govde_2,"(fiil soylu-yüklem)+");} // fiil govdesi
         
         else 
         {
         String aday_govde_22=fiil_istisna_kontrol_3(kelimenin_eksiz_hali);
         if (aday_govde_22.equals(kelimenin_eksiz_hali)==false) {govdeyazdir(aday_govde_22);etiketyazdir(aday_govde_22,"(fiil soylu-yüklem)-");}  // fiil govdesi
          
         else
         {
         String aday_govde_3=olumsuz_fiil_kontrol(kelimenin_eksiz_hali); // fiile gelen olumsuzluk ekleri varsa atılıyor.
         if (aday_govde_3.equals(kelimenin_eksiz_hali)==false) {govdeyazdir(aday_govde_3);etiketyazdir(aday_govde_3,"(fiil soylu-yüklem)?");}
                    
         else 
         {
         String aday_govde_4=kuralli_bilesik_fiil_ek_cikar(aday_govde_3);
         if (aday_govde_4.equals(aday_govde_3)==false) {govdeyazdir(aday_govde_4);etiketyazdir(aday_govde_4,"(fiil soylu-yüklem)/");}
          
         else 
         {
         if (turemis_fiil_kontrol(aday_govde_4)==true) {govdeyazdir(aday_govde_4);etiketyazdir(aday_govde_4,"(fiil soylu-yüklem)!");}
         
         else
         { 
         String enyakin_fiil_govde=ortak_ek_kontrol(kelimenin_ilk_hali,kelimenin_eksiz_hali);
         if (enyakin_fiil_govde.equals(kelimenin_ilk_hali)==false && kelimenin_eksiz_hali.equals(kelimenin_ilk_hali)==false) {govdeyazdir(enyakin_fiil_govde);etiketyazdir(enyakin_fiil_govde,"(fiil soylu-yüklem)%");}
         
         
         //------------------------------------------isim soylu kelimeler (bir ek alanlar)
         
         else
         {               
         String aday_govde1=ekzarf_eki_cikar(kelimenin_ilk_hali);
         if (isim_soylu_agac.search(aday_govde1)==true || yapim_eki_kontrol(aday_govde1)==true) {govdeyazdir(aday_govde1);etiketyazdir(aday_govde1,turbul(aday_govde1));} // arkadaşken > arkadaş
         
         else
         {
         String aday_govde2=sahiplik_yoksunluk_eki_cikar(kelimenin_ilk_hali);
         if (isim_soylu_agac.search(aday_govde2)==true || yapim_eki_kontrol(aday_govde2)==true) {govdeyazdir(aday_govde2);etiketyazdir(aday_govde2,turbul(aday_govde2));} // akılsız > akıl
         
         else
         {
         String aday_govde3=fiilimsi_eki_cikar(kelimenin_ilk_hali);
         if (fiil_soylu_agac.search(aday_govde3)==true) {govdeyazdir(aday_govde3);etiketyazdir(aday_govde3,"(isim soylu-fiilimsi)--");} // geldiği > gel
         
         else
         {
         
         File ekfiil_ekler = new File("ekfiil_ekler.txt");
         String aday_govde4=ekfiil_eki_cikar(ekfiil_ekler,kelimenin_ilk_hali);
         if (isim_soylu_agac.search(aday_govde4)==true || yapim_eki_kontrol(aday_govde4)==true) {govdeyazdir(aday_govde4);etiketyazdir(aday_govde4,turbul(aday_govde4));} // arkadaşmış > arkadaş
         
         else
         {
         String aday_govde5=ilgi_eki_cikar(kelimenin_ilk_hali);
         if (isim_soylu_agac.search(aday_govde5)==true || yapim_eki_kontrol(aday_govde5)==true) {govdeyazdir(aday_govde5);etiketyazdir(aday_govde5,turbul(aday_govde5));} // bugünkü > bugün
         
         else
         {    
         String aday_govde6=durum_eki_cikar(kelimenin_ilk_hali);
         aday_govde6=yumusama(aday_govde6);
         aday_govde6=dusme(aday_govde6);
         aday_govde6=durum_ek_kontrol_3(aday_govde6);
         if (isim_soylu_agac.search(aday_govde6)==true || yapim_eki_kontrol(aday_govde6)==true) {govdeyazdir(aday_govde6);etiketyazdir(aday_govde6,turbul(aday_govde6));} // arkadaşın kalemi > arkadaş
         
         else 
         {
         String aday_govde7=iyelik_eki_cikar(kelimenin_ilk_hali);
         aday_govde7=yumusama(aday_govde7);
         aday_govde7=dusme(aday_govde7);
         if (isim_soylu_agac.search(aday_govde7)==true || yapim_eki_kontrol(aday_govde7)==true) {govdeyazdir(aday_govde7);etiketyazdir(aday_govde7,turbul(aday_govde7));} // arkadaşım > arkadaş
         
         else 
         {
         String aday_govde8=cogul_eki_cikar(kelimenin_ilk_hali);
         if (isim_soylu_agac.search(aday_govde8)==true || yapim_eki_kontrol(aday_govde6)==true) {govdeyazdir(aday_govde8);etiketyazdir(aday_govde8,turbul(aday_govde8));} //arkadaşlar > arkadaş
         
         else 
         {
         if (yapim_eki_kontrol(kelimenin_ilk_hali)==true) {govdeyazdir(kelimenin_ilk_hali);etiketyazdir(kelimenin_ilk_hali,turbul(kelimenin_ilk_hali));} // kitaplık
         
         //------------------------------------------isim soylu kelimeler (birden fazla ek alanlar)
         
         else
         { 
         String kelimenin_ekzarfsiz_hali=ekzarf_eki_cikar(kelimenin_ilk_hali);
         File ekfiil_ekler1 = new File("ekfiil_ekler.txt");
         String kelimenin_ekfiilsiz_hali=ekfiil_eki_cikar(ekfiil_ekler1,kelimenin_ekzarfsiz_hali);
         kelimenin_ekfiilsiz_hali=ekfiil_kontrol_1(kelimenin_ekfiilsiz_hali);
         if (isim_soylu_agac.search(kelimenin_ekfiilsiz_hali)==true || yapim_eki_kontrol(kelimenin_ekfiilsiz_hali)==true) {govdeyazdir(kelimenin_ekfiilsiz_hali);etiketyazdir(kelimenin_ekfiilsiz_hali,turbul(kelimenin_ekfiilsiz_hali));} //(Örn:arkadaş-mış > isim gövde:arkadaş)
         
         else
         {
         String kelimenin_ilgieksiz_hali=ilgi_eki_cikar(kelimenin_ekfiilsiz_hali);
         if (isim_soylu_agac.search(kelimenin_ilgieksiz_hali)==true || yapim_eki_kontrol(kelimenin_ilgieksiz_hali)==true) {govdeyazdir(kelimenin_ilgieksiz_hali);etiketyazdir(kelimenin_ilgieksiz_hali,turbul(kelimenin_ilgieksiz_hali));} //(Örn:bugün-kü > isim gövde:bugün)
                   
         else
         {   
         String kelimenin_durumeksiz_hali=durum_eki_cikar(kelimenin_ilgieksiz_hali);
         kelimenin_durumeksiz_hali=yumusama(kelimenin_durumeksiz_hali);
         kelimenin_durumeksiz_hali=dusme(kelimenin_durumeksiz_hali);
         kelimenin_durumeksiz_hali=durum_ek_kontrol_2(kelimenin_durumeksiz_hali);
         if (isim_soylu_agac.search(kelimenin_durumeksiz_hali)==true || yapim_eki_kontrol(kelimenin_durumeksiz_hali)==true) {govdeyazdir(kelimenin_durumeksiz_hali);etiketyazdir(kelimenin_durumeksiz_hali,turbul(kelimenin_durumeksiz_hali));} //(Örn:arkadaş-ın kalemi > isim gövde:arkadaş)
                   
         else 
         {
         kelimenin_durumeksiz_hali=durum_ek_kontrol_1(kelimenin_ilgieksiz_hali,kelimenin_durumeksiz_hali);
         String kelimenin_iyelikeksiz_hali=iyelik_eki_cikar(kelimenin_durumeksiz_hali);
         kelimenin_iyelikeksiz_hali=yumusama(kelimenin_iyelikeksiz_hali);
         kelimenin_iyelikeksiz_hali=dusme(kelimenin_iyelikeksiz_hali);
         kelimenin_iyelikeksiz_hali=iyelik_ek_kontrol(kelimenin_durumeksiz_hali,kelimenin_iyelikeksiz_hali);
         kelimenin_iyelikeksiz_hali=iyelik_ek_kontrol_2(kelimenin_durumeksiz_hali,kelimenin_iyelikeksiz_hali);
         kelimenin_iyelikeksiz_hali=iyelik_ek_kontrol_3(kelimenin_iyelikeksiz_hali);
         if (isim_soylu_agac.search(kelimenin_iyelikeksiz_hali)==true || yapim_eki_kontrol(kelimenin_iyelikeksiz_hali)==true) {govdeyazdir(kelimenin_iyelikeksiz_hali);etiketyazdir(kelimenin_iyelikeksiz_hali,turbul(kelimenin_iyelikeksiz_hali));} //(Örn:arkadaş-ım > isim gövde:arkadaş)
         
         else 
         {
         String kelimenin_coguleksiz_hali=cogul_eki_cikar(kelimenin_iyelikeksiz_hali);
         if (isim_soylu_agac.search(kelimenin_coguleksiz_hali)==true || yapim_eki_kontrol(kelimenin_coguleksiz_hali)==true) {govdeyazdir(kelimenin_coguleksiz_hali);etiketyazdir(kelimenin_coguleksiz_hali,turbul(kelimenin_coguleksiz_hali));} //(Örn:arkadaş-lar > isim gövde:arkadaş)
                
         else 
         {
         String kelimenin_sahiplik_yoksunluk_eksiz_hali=sahiplik_yoksunluk_eki_cikar(kelimenin_coguleksiz_hali);
         if (isim_soylu_agac.search(kelimenin_sahiplik_yoksunluk_eksiz_hali)==true || yapim_eki_kontrol(kelimenin_ekfiilsiz_hali)==true) {govdeyazdir(kelimenin_sahiplik_yoksunluk_eksiz_hali);etiketyazdir(kelimenin_sahiplik_yoksunluk_eksiz_hali,turbul(kelimenin_sahiplik_yoksunluk_eksiz_hali));} //(Örn:akıl-lı > isim gövde:akıl)

         else 
         {
         if (yapim_eki_kontrol(kelimenin_sahiplik_yoksunluk_eksiz_hali)==true) {govdeyazdir(kelimenin_sahiplik_yoksunluk_eksiz_hali);etiketyazdir(kelimenin_sahiplik_yoksunluk_eksiz_hali,turbul(kelimenin_sahiplik_yoksunluk_eksiz_hali));} //isim govde (yapım eki almış ancak sözlükte bulunmayan isim gövdesi tespiti)

         //------------------------------------------------fiilimsiler
         
         else
         {
         String kelimenin_fiilimsi_eksiz_hali=fiilimsi_eki_cikar(kelimenin_sahiplik_yoksunluk_eksiz_hali);
         if (fiil_soylu_agac.search(kelimenin_fiilimsi_eksiz_hali)==true) {govdeyazdir(kelimenin_fiilimsi_eksiz_hali);etiketyazdir(kelimenin_fiilimsi_eksiz_hali,"(isim soylu-fiilimsi)+1");}
         
         else
         {
         String kelimenin_olumsuz_eksiz_hali=olumsuz_fiil_kontrol(kelimenin_fiilimsi_eksiz_hali);
         if (kelimenin_olumsuz_eksiz_hali.equals(kelimenin_fiilimsi_eksiz_hali)==false) {govdeyazdir(kelimenin_olumsuz_eksiz_hali);etiketyazdir(kelimenin_olumsuz_eksiz_hali,"(isim soylu-fiilimsi)+2");}
         
         else
         {
         String kelimenin_kuralli_bilesik_eksiz_hali=kuralli_bilesik_fiil_ek_cikar(kelimenin_olumsuz_eksiz_hali);    
         if (kelimenin_kuralli_bilesik_eksiz_hali.equals(kelimenin_olumsuz_eksiz_hali)==false) {govdeyazdir(kelimenin_kuralli_bilesik_eksiz_hali);etiketyazdir(kelimenin_kuralli_bilesik_eksiz_hali,"(isim soylu-fiilimsi)+3");}
         
         else
         {
         if (turemis_fiil_kontrol(kelimenin_kuralli_bilesik_eksiz_hali)==true) {govdeyazdir(kelimenin_kuralli_bilesik_eksiz_hali);etiketyazdir(kelimenin_kuralli_bilesik_eksiz_hali,"(isim soylu-fiilimsi)+4");}
         
         else
         {
         String aday_govde_7=fiil_govde_tahmin_2(kelimenin_ilk_hali, aday_govde_4);
         if (aday_govde_7.equals(aday_govde_4)==false) {govdeyazdir(aday_govde_7); etiketyazdir(aday_govde_7,"(fiil soylu-yüklem)");}
         
         else
         {
         String aday_govde_8=isim_govde_tahmin(kelimenin_ilk_hali,kelimenin_kuralli_bilesik_eksiz_hali);
         if (aday_govde_8.equals(kelimenin_kuralli_bilesik_eksiz_hali)==false) {govdeyazdir(aday_govde_8);etiketyazdir(aday_govde_8,turbul(aday_govde_8));}
         
         else
         {
         govdeyazdir(kelimenin_kuralli_bilesik_eksiz_hali);etiketyazdir(kelimenin_kuralli_bilesik_eksiz_hali,"(isim soylu-isim)#");
         }
}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}

//---------------fiil soylu kelimeler---------------------------------------------------

public static String fiil_istisna_kontrol_1(String str) {
    
    // bazı fiil ekleri ile fiil gövdesi karıştığı için kontrol yapılıyor.
    // (Örn: koyun > ko > koy)
            
    String[] ekler={"yın","yin","yun","yün","u"};
    String govde=str;
    
    for (String i:ekler)
        {
          if (cikan_ek_1.startsWith(i)==true)
           {
               String str1=str.concat(cikan_ek_1.substring(0,1));
               if (fiil_soylu_agac.search(str1)==true)
               {
               govde=str1; break;
               }
           }
        }
    return govde;
}

public static String fiil_istisna_kontrol_2(String str) {
    
    // bazı fiil ekleri ile fiil gövdesi karıştığı için kontrol yapılıyor.
    // (Örn: vurmuş > v > vur)
    // (Örn: vurdurmuş > vurd > vurdur)
    
    String govde=str;    
    String[] ekler={"ar","er","ır","ir","ur","ür"};
        
    for (String i:ekler)
        {
          if (cikan_ek_1.startsWith(i)==true)
           {
               String str1=str.concat(cikan_ek_1.substring(0,2));
               String str2=kuralli_bilesik_fiil_ek_cikar(str1);
               if (fiil_soylu_agac.search(str2)==true || turemis_fiil_kontrol(str2)==true)
               {
               govde=str2; break;
               }
           }
        }
    return govde;
    }

public static String fiil_istisna_kontrol_3(String str) {
    
    // 5 adet farklı istisna durumlar kontrol ediliyor.
    
    String govde=str;

    if(str.equals("d")) {str=str.replaceAll("d","de"); govde=str;} //istisna durum 1

    else {
    if (str.equals("di")) {str=str.replaceAll("di","de"); govde=str;} //istisna durum 2

    else {
    if(str.equals("y")) {str=str.replaceAll("y","ye"); govde=str;} //istisna durum 3

    else {
    if (str.equals("yi")) {str=str.replaceAll("yi","ye"); govde=str;} //istisna durum 4

    else {
    if (str.endsWith("d")==true) // istisna durum 5 - Ünsüz Yumuşaması (Örn: gidiyor > gid > git)
        {
        str=replaceLast(str,"d","t");
        if (fiil_soylu_agac.search(str)==true) govde=str;
        }
        }
    }}}
  return govde;
}

public static String olumsuz_fiil_kontrol(String str) {
        
    // Fiillere gelen olumsuzluk eki (-ma, -me) çekim eki olarak kabul edilmiştir. 
    // Bu nedenle fiile gelen ekler (kip-şahıs) çıkarıldıktan sonra kalan kelimede olumsuzluk eki mevcut ise çıkarılır.
    // Örn-1: almadım > alma > al
    // Örn-2: almıyorum > alm > al
    // Örn-3: yamadık > yama > yama
    // Örn-4: yamamıyoruz > yamam > yama
    // Örn-5: aldırıvermedim > aldırıverme > aldırıver > aldır
       
    String govde=str;
    String[] olumsuz_fiil_eki={"ma","me","m"};
        for (String i:olumsuz_fiil_eki)
        {
            if (str.endsWith(i)==true)
            {
              String str1=replaceLast(str,i,"");
              String str2=kuralli_bilesik_fiil_ek_cikar(str1);
              if (fiil_soylu_agac.search(str2)==true || turemis_fiil_kontrol(str2)==true) 
                {
                govde=str2;break;
                }
            }
        } 
     return govde;
    }    

public static String kuralli_bilesik_fiil_ek_cikar(String str) {
        
    // Fiillere gelen kurallı bileşik fiil ekleri (tezlik, yeterlilik, yaklaşma ve sürerlilik durumları) çekim eki olarak kabul edilmiştir. 
    // Bu nedenle fiile gelen ekler sırasıyla (kip-şahıs, olumsuzluk) çıkarıldıktan sonra kalan kelimede kurallı bileşik fiil eklerinden biri mevcut ise çıkarılır.
    // Örn-1: geliverdim > geliver > gel
    // Örn-2: gelivermedim > geliverme > geliver > gel
    // Örn-3: dönüştürtülüvermedi > dönüştürtülüverme > dönüştürtülüver > dönüştürtül
   
   String govde=str;
   File kuralli_bilesik_fiil_ekler = new File("kuralli_bilesik_fiil_ekler.txt");
   String str1=ek_cikar(kuralli_bilesik_fiil_ekler, str);
    if (fiil_soylu_agac.search(str1)==true || turemis_fiil_kontrol(str1)==true) 
        {
        govde=str1;
        }
      return govde;
    }



public static String ortak_ek_kontrol(String str1, String str2) {
        
    // Hem isim gövdesine hem de fiil gövdesine gelebilen ekler kontrol ediliyor.
    // Sadece fiil gövdesine gelebilen ekleri alan fiiller ayrıştırılıyor.(Örn: söylüyor > söyl > söyle)
    // Aşağıda belirtilen ekleri alan kelimelerin isim soylu kelimeler olabilme ihtimali yüksek olduğundan bu kelimelere herhangi bir işlem yapılmadan geçiliyor.
    // Böylece bu tür kelimelerin yapısı bozulmadan isim gövdesini bulan algoritmaya girmesi sağlanıyor.
    // (Örn: kelimeler > kelim > kelimeler)
            
    String[] ekler={"alar","eler","ın","in","un","ün","a","e","m","n","z"};
    Boolean ortak_ek=false;  
    String govde=str1;
    for (String i:ekler)
        {
           if (str1.endsWith(i)==true)
           {
            ortak_ek=true; break;
           }
        }
    
           if (ortak_ek==false) 
           {
            str1=fiil_govde_tahmin_1(str2);
            if (fiil_soylu_agac.search(str1)==true)  
                {
                  govde=str1;
                }
           }
         
      return govde;
    }  

public static String fiil_govde_tahmin_1(String str) {
    
    String govde=str;
    if (cikan_ek_1 == null!=true)
    {
    String str1=str.concat(cikan_ek_1.substring(0,1));
    if (fiil_soylu_agac.search(str1)==true) govde=str1;
    else
      {
        String str2=enyakin_fiil_govdebul(str); 
        if (fiil_soylu_agac.search(str2)==true)
        govde=str2;
      }
    }
  return govde;
}

public static String fiil_govde_tahmin_2(String str1, String str2) {
    
    String govde=str2;
    String str=fiil_govde_ek_geri_al(str1,str2);
    if (str.equals(str2)==false) govde=str;
    else {
          govde=enyakin_fiil_govdebul(str2);
         }
return govde;
}

//---------------isim soylu kelimeler---------------------------------------------------

 public static String ekzarf_eki_cikar(String str) {
     
     // arkadaşken > arkadaş
     // arabayla > araba
     
     String[] ekler={"casına","cesine","yken","ken","yla","yle","ca","ce","ça","çe","la","le"};
        for (String i:ekler)
        {
            if (str.endsWith(i)==true)
            {
               str=replaceLast(str,i,"");break;
            }
        }
        return str;
    }
 
 public static String ekfiil_kontrol_1 (String str1) {
        
       // algoritma ilerlerken ilgili türdeki kelime yapısının bozulmadan varması gereken fonksiyona ulaşması sağlanıyor.
       // olabilecek bozulmalar kontrol ediliyor ve düzeltiliyor.
       // gördüğüm > gördüğ > gördüğü
        
        if (str1.endsWith("ğ")==true && ekfiil_eki == null!=true)
            {
               String str2=yumusama(str1);
               if (str1.equals(str2)==true)
               str1=str1.concat(ekfiil_eki.substring(0,1));
            }
        return str1;
    } 
 
public static String durum_eki_cikar(String str) {
    
    // defterde > defter
    // okuldan > okul
    
    String[] ekler={"dan","den","tan","ten","da","de","ın","in","un","ün","ta","te","ya","ye","yı","yi","yu","yü","a","e","ı","i","u","ü"};
        for (String i:ekler)
        {
            if (str.endsWith(i)==true)
            {
               str=replaceLast(str,i,"");
               durum_eki=i;
               break;
            }
        }
        return str;
    }    

public static String durum_ek_kontrol_1(String str1, String str2) {
    
       // algoritma ilerlerken ilgili türdeki kelime yapısının bozulmadan varması gereken fonksiyona ulaşması sağlanıyor.
       // olabilecek bozulmalar kontrol ediliyor ve düzeltiliyor.
       // (Örn: kedisi > kedis ---> böyle olunca iyelik eki -si bulunamıyor)
       // (Örn: kedisi > kedisi ---> böyle olunca iyelik eki -si bulunabiliyor)
        
        String[] ekler={"ı","i","u","ü"};
        for (String i:ekler)
        {
            if (str1.endsWith(i)==true)
            {
                if (str2.endsWith("s")==true || str2.endsWith("l")==true)
                {
                    str2=str2.concat(i);
                    break;
                }
            }
        }
      return str2;
    } 

public static String durum_ek_kontrol_2(String str1) {
    
     // algoritma ilerlerken ilgili türdeki kelime yapısının bozulmadan varması gereken fonksiyona ulaşması sağlanıyor.
     // olabilecek bozulmalar kontrol ediliyor ve düzeltiliyor.
     // geldiğin > geldiğ > geldiği
        
        if (str1.endsWith("ğ")==true && durum_eki == null!=true)
            {
               String str2=yumusama(str1);
               if (str1.equals(str2)==true)
               str1=str1.concat(durum_eki.substring(0,1));
            }
        return str1;
    }

public static String durum_ek_kontrol_3(String str1) {
        
     // algoritma ilerlerken ilgili türdeki kelime yapısının bozulmadan varması gereken fonksiyona ulaşması sağlanıyor.
     // olabilecek bozulmalar kontrol ediliyor ve düzeltiliyor.    
     // hayata > haya (haya ile hayat kelimesi karıştırılmamalıdır)
    
    String govde=str1;    
    if (durum_eki == null!=true)
    {
        if (durum_eki.startsWith("t")==true)
        {
            String str2=str1.concat(durum_eki.substring(0,1));
            if (isim_soylu_agac.search(str2)==true) govde=str2;
        }
    }
  return govde;
}

public static String iyelik_eki_cikar(String str) {
    
    // arabamız > araba
    
        String[] ekler={"ımız","ınız","imiz","iniz","ları","leri","umuz","unuz","ümüz","ünüz","mız","miz","muz","müz","nız","niz","nuz","nüz","sın","sin","ım","ın","im","in","sı","si","su","sü","um","un","üm","ün","ı","i","m","n","u","ü"}; 
        for (String i:ekler)
        {
            if (str.endsWith(i)==true)
            {
               str=replaceLast(str,i,"");
               iyelik_eki=i;
               break;
            }
        }
      return str;
    }    

public static String iyelik_ek_kontrol(String str1, String str2) {
    
        // algoritma ilerlerken ilgili türdeki kelime yapısının bozulmadan varması gereken fonksiyona ulaşması sağlanıyor.
        // olabilecek bozulmalar kontrol ediliyor ve düzeltiliyor. 
        // (Örn: sorusuna > sorusun > sorus ---> böyle olunca iyelik eki -su bulunamıyor)
        // (Örn: kasına > kasın > kas ---> isim gövdesi ile karıştırılmamalıdır)
        
        String[] ekler={"ın","in","un","ün"};
        String govde=str2;
        for (String i:ekler)
        {
            if (str1.endsWith(i)==true && str2.endsWith("s")==true)
            {
            str2=replaceLast(str2,"s","");
            if (isim_soylu_agac.search(str2)==true)  
                {
                  govde=str2; break;
                }
             }
        } 
      return govde;
    }  

public static String iyelik_ek_kontrol_2(String str1, String str2) {
     
        // algoritma ilerlerken ilgili türdeki kelime yapısının bozulmadan varması gereken fonksiyona ulaşması sağlanıyor.
        // olabilecek bozulmalar kontrol ediliyor ve düzeltiliyor. 
        // (Örn: akıllı > akıll ---> böyle olunca sahiplik yoksunluk eki -lı bulunamıyor)
                
        String[] ekler={"ı","i","u","ü"};
        for (String i:ekler)
        {
            if (str1.endsWith(i)==true)
            {
                if (str2.endsWith("l")==true)
                {
                    str2=str2.concat(i);
                    break;
                }
            }
        }
      return str2;
    }

public static String iyelik_ek_kontrol_3 (String str1) {
        
    // algoritma ilerlerken ilgili türdeki kelime yapısının bozulmadan varması gereken fonksiyona ulaşması sağlanıyor.
    // olabilecek bozulmalar kontrol ediliyor ve düzeltiliyor.        
    // geldiğiniz > geldiğ > geldiği
        
        if (str1.endsWith("ğ")==true && iyelik_eki == null!=true)
            {
               String str2=yumusama(str1);
               if (str1.equals(str2)==true)
               str1=str1.concat(iyelik_eki.substring(0,1));
            }
        return str1;
    } 

public static String ilgi_eki_cikar(String str) {
    
    // dünkü > dün
    
      String[] ekler={"ki","kü"};
        for (String i:ekler)
        {
            if (str.endsWith(i)==true)
            {
               str=replaceLast(str,i,"");break;
            }
        }
        return str;
    } 

public static String cogul_eki_cikar(String str) {
    
    // kalemler > kalem
    
      String[] ekler={"lar","ler"};
        for (String i:ekler)
        {
            if (str.endsWith(i)==true)
            {
               str=replaceLast(str,i,"");break;
            }
        }
        return str;
    }    

public static String sahiplik_yoksunluk_eki_cikar(String str) {
        
    // (Örn: akıl-lı > akıl (sahiplik çekim eki)
    // (Örn: akıl-sız > akıl (yoksunluk çekim eki)
    // İsim ile bütünleşen sahiplik ve yoksunluk çekim ekleri çıkarılıyor.
    
    String[] ekler={"sız","siz","suz","süz","lı","li","lu","lü"};
        for (String i:ekler)
        {
            if (str.endsWith(i)==true)
            {
               str=replaceLast(str,i,"");break;
            }
        }
        return str;
    } 

 public static String fiilimsi_eki_cikar(String str) {
        
    // (Örn: gülen > gül (fiilimsi çekim eki)
    // (Örn: ölesiye > öl (fiilimsi çekim eki)
    // Fiil soylu kelimelere eklenen fiilimsi ekleri çıkarılıyor.
    
    Boolean x=false;
    try {
    File fiilimsi_ekler = new File("fiilimsi_ekler.txt");
    BufferedReader reader;
    reader = new BufferedReader(new FileReader(fiilimsi_ekler));
    String satir = reader.readLine();
       while (satir!=null) 
       {
        x=str.endsWith(satir);
          if(x==true)
            {
             str=replaceLast(str,satir,""); break;
            }
        satir = reader.readLine();
        }
        reader.close();
        } catch(IOException e) {}
      return str;
    }
 
public static String isim_govde_tahmin(String str1, String str2) {
    
    String govde=str2;
    String str=isim_govde_ek_geri_al(str1,str2);
    if (str.equals(str2)==false) govde=str;
    else {
          govde=enyakin_isim_govdebul(str2);
         }
return govde;
}
 
public static int oge_no_bul(String str, String[] dizi){
    
    int oge_no=0;
    for (int i=0; i<dizi.length; i++) 
        {
        if (dizi[i].equals(str)) 
            {
               oge_no=i+1; break;
            }
        } 
    return oge_no;
    }



public void program3(){
    
    File fiil_soylu = new File("fiil_soylu.txt");
    metinagaciolustur(fiil_soylu, fiil_soylu_agac);
    
    File isim_soylu = new File("isim_soylu.txt");
    metinagaciolustur(isim_soylu, isim_soylu_agac);
    
    File essesli = new File("essesli.txt");
    metinagaciolustur(essesli, essesli_agac);
    
    File isim = new File("isim.txt");
    metinagaciolustur(isim, isim_agac);
    
    File sifat = new File("sifat.txt");
    metinagaciolustur(sifat, sifat_agac);
    
    File zarf = new File("zarf.txt");
    metinagaciolustur(zarf, zarf_agac);
    
    File zamir = new File("zamir.txt");
    metinagaciolustur(zamir, zamir_agac);
    
    File baglac = new File("baglac.txt");
    metinagaciolustur(baglac, baglac_agac);
    
    File edat = new File("edat.txt");
    metinagaciolustur(edat, edat_agac);
    
    File unlem = new File("unlem.txt");
    metinagaciolustur(unlem, unlem_agac);
    
    metni_cumle_ve_kelimelere_ayir();
    System.out.println("Gövdeleme ve Etiketleme işlemi tamamlandı...");
    
    }

}