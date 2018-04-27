
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Lenovo
 */
public class GovdeTurk {
    
    static Trie fiil_soylu_agac=new Trie();
    static Trie isim_soylu_agac=new Trie();
    static Trie essesli_agac=new Trie();
    static Trie isim_agac=new Trie();
    static Trie sifat_agac=new Trie();
    static Trie zarf_agac=new Trie();
    static Trie zamir_agac=new Trie();
    static Trie baglac_agac=new Trie();
    static Trie edat_agac=new Trie();
    static Trie unlem_agac=new Trie();
    static String cikan_ek,cikan_ek_1,cikan_ek_2,cikan_ek_3,durum_eki,iyelik_eki,ekfiil_eki,a;
    static String dosyaYolu; 
                
    private static int minimum(int a, int b, int c) {                            
        return Math.min(Math.min(a, b), c);                                      
    }           
    
    public static int computeLevenshteinDistance(CharSequence lhs, CharSequence rhs) {      
        int[][] distance = new int[lhs.length() + 1][rhs.length() + 1];        
                                                                                 
        for (int i = 0; i <= lhs.length(); i++)                                 
            distance[i][0] = i;                                                  
        for (int j = 1; j <= rhs.length(); j++)                                 
            distance[0][j] = j;                                                  
                                                                                 
        for (int i = 1; i <= lhs.length(); i++)                                 
            for (int j = 1; j <= rhs.length(); j++)                             
                distance[i][j] = minimum(                                        
                        distance[i - 1][j] + 1,                                  
                        distance[i][j - 1] + 1,                                  
                        distance[i - 1][j - 1] + ((lhs.charAt(i - 1) == rhs.charAt(j - 1)) ? 0 : 1));
                                                                                 
        return distance[lhs.length()][rhs.length()];                           
    } 
    
    public static String replaceLast(String text, String regex, String replacement) {
        return text.replaceFirst("(?s)(.*)" + regex, "$1" + replacement);
    }
    
    public static void metinagaciolustur(File in, Trie agac) {
        
    // isim_soylu, fiil_soylu, eş sesli, isim, sıfat, zarf, zamir, bağlaç, edat, ünlem sözlüğü olmak üzere 10 adet sözlük bulunmaktadır.
    // program çalıştığı anda her sözlük metin ağacı veri yapısı (trie) ile ön belleğe yerleşir.
    
    try {
         BufferedReader reader;
         reader = new BufferedReader(new FileReader(in));
         String satir = reader.readLine();
            while (satir!=null) {
                agac.insert(satir);
                satir = reader.readLine();
              }
            reader.close();
        } catch(IOException e) {
      }
    }
     
    public static String ek_cikar(File in, String str) {
    try {
    Boolean b;
        BufferedReader reader;
        reader= new BufferedReader(new FileReader(in));
        String satir = reader.readLine();
            while (satir!=null) {
                b=str.endsWith(satir);
                  if(b==true)
                   {
                    str=replaceLast(str,satir,"");
                    cikan_ek=satir;
                    break;
                   }
               satir = reader.readLine();
              }
        reader.close();
        } catch(IOException e) {
      }
    return str;
    }
    
    public static String ekfiil_eki_cikar(File in, String str) {
    try {
    Boolean b;
        BufferedReader reader;
        reader= new BufferedReader(new FileReader(in));
        String satir = reader.readLine();
            while (satir!=null) {
                b=str.endsWith(satir);
                  if(b==true)
                   {
                    str=replaceLast(str,satir,"");
                    ekfiil_eki=satir;
                    break;
                   }
               satir = reader.readLine();
              }
        reader.close();
        } catch(IOException e) {
      }
    return str;
    }
    
    public static String yumusama(String str) {
       String yumusak_isim_kok=str;
         if (str.endsWith("ng")==true)
            {
             str=replaceLast(str,"ng","nk");
             if (isim_soylu_agac.search(str)==true || yapim_eki_kontrol(str)==true) yumusak_isim_kok=str;
            } 
            else if (str.endsWith("b")==true)
            {
             str=replaceLast(str,"b","p");
             if (isim_soylu_agac.search(str)==true || yapim_eki_kontrol(str)==true) yumusak_isim_kok=str;
            }
            else if (str.endsWith("c")==true)
            {
             str=replaceLast(str,"c","ç");
             if (isim_soylu_agac.search(str)==true || yapim_eki_kontrol(str)==true) yumusak_isim_kok=str;
            }
            else if (str.endsWith("d")==true)
            {
             str=replaceLast(str,"d","t");
             if (isim_soylu_agac.search(str)==true || yapim_eki_kontrol(str)==true) yumusak_isim_kok=str;
            }
            else if (str.endsWith("ğ")==true)
            {
             str=replaceLast(str,"ğ","k");
             if (isim_soylu_agac.search(str)==true || yapim_eki_kontrol(str)==true) yumusak_isim_kok=str;
            }
        return yumusak_isim_kok;
    }    
      
    public static String dusme(String str) {
    String govde = str;
    String str1 = str,str2 = str;
    String [] sessiz_harfler= {"b","c","ç","d","f","g","ğ","h","j","k","l","m","n","p","r","s","ş","t","v","y","z"};
    String [] dar_sesli_harfler={"ı","i","u","ü"};
    
    for (String i:sessiz_harfler)
      {
       if (str.endsWith(i)==true)
            {
               str1=replaceLast(str,i,"");
            
               for (String j:sessiz_harfler)
                    {
                        if (str1.endsWith(j)==true)
                        {
                            str2=replaceLast(str1,j,"");
                        }}}}
    int a=govde.length()- 1;
    int b=govde.length()- str2.length();
    if (b==2) 
    {
     String sonharf=(govde.substring(a));
        for (String k:dar_sesli_harfler)
        {
        str=str1.concat(k).concat(sonharf);
        if (isim_soylu_agac.search(str)==true) govde=str;
        }
        }
      return govde;
    }
     
    public static String fiil_govde_ek_geri_al(String str1, String str2) {

    // (str1=kedinin, str2=ked)
    
    String govde=str2;
    int x=str1.length();
    int y=str2.length();
    int z=x-y;
    if (z>0)
    {
      for (int i=1; i<=z; i++)
       {
        String str=str1.substring(0,y+i);
        if (fiil_soylu_agac.search(str)==true || turemis_fiil_kontrol(str)==true) {govde=str; break;}
       }
    }
    return govde;
   }
    
   public static Boolean turemis_fiil_kontrol(String str) {
        
    // Fiil köküne eklenme sırasına göre işteş, oldurgan, ettirgen, dönüşlü veya edilgen ek alan fiiller türemiş fiillerdir.
    // Fiil köküne eklenme sırasına göre işteş, oldurgan, ettirgen, dönüşlü veya edilgen ek alan bazı fiiller sözlükte yer almıyor. 
    // Bu sebeple fiilerin türemiş fiil olup olmadıkları konrol ediliyor.
        
    Boolean turemis_fiil = false;
    File turemis_fiil_ekler = new File("turemis_fiil_ekler.txt");
    String str1=ek_cikar(turemis_fiil_ekler,str);
    cikan_ek_2=cikan_ek;
    if (str1.endsWith("d")==true)
        {
        str1=replaceLast(str1,"d","t");
        }
    
        if (fiil_soylu_agac.search(str1)==true) turemis_fiil=true;
        else
         {
            if (cikan_ek_2 == null!=true && isim_soylu_agac.search(str1)==false)
            {
             String str2=str1.concat(cikan_ek_2.substring(0,1));
             if (fiil_soylu_agac.search(str2)==true) turemis_fiil=true;       
            }
          }   
       return turemis_fiil;
}  
    
    public static String isim_govde_ek_geri_al(String str1, String str2) {

    // (str1=kedinin, str2=ked)
    
    String govde=str2;
    int x=str1.length();
    int y=str2.length();
    int z=x-y;
    if (z>0)
    {
      for (int i=1; i<=z; i++)
       {
        String str=str1.substring(0,y+i);
        if (isim_soylu_agac.search(str)==true || yapim_eki_kontrol(str)==true) {govde=str; break;}
       }
    }
    return govde;
   }
    
   
   
   public static Boolean ekfiil_kontrol(String str) {
   
   // kelimenin ekfiil alıp almadığı sorgulanıyor.
        
    Boolean ekfiil = false;
    String[] ekfiil_dizi={"var","yok","değil"};
    
    for (String dizi: ekfiil_dizi)
    {
    if (str.equals(dizi)==true) {ekfiil=true; break;}
    }
    
    if (ekfiil.equals(false)) 
     {
        File ekfiil_ekler = new File("ekfiil_ekler.txt");
        String str1=ek_cikar(ekfiil_ekler,str);
        if (str.equals(str1)==false)    
        {
        str1=yumusama(str1);
        if (isim_soylu_agac.search(str1)==true || yapim_eki_kontrol(str1)==true) ekfiil=true;
        }
      }
    return ekfiil;
   }
    
   public static String enyakin_fiil_govdebul(String str) {
        
   // hiç bir adımda gövdesi bulunamayan fiiler bu adımda belli kurallar dahilinde fiil sözlüğünde en yakın gövdeye eşleşir.
    
      try {
      File sozluk = new File("fiil_soylu.txt");
      BufferedReader reader;
      reader = new BufferedReader(new FileReader(sozluk));
      String satir = reader.readLine();
             while (satir!=null) 
                {
                if (satir.startsWith(str)==true)
                {
                    if (computeLevenshteinDistance(satir,str)==1)
                        { 
                            str=satir; break;
                        }
                }
                satir = reader.readLine();
                }
             reader.close();
        } catch(IOException e) {
      }
    return str;
    } 
    
    public static String enyakin_isim_govdebul(String str) {
        
    // hiç bir adımda gövdesi bulunamayan isimler bu adımda belli kurallar dahilinde isim sözlüğünde en yakın gövdeye eşleşir.
    
      try {
      File sozluk = new File("isim_soylu.txt");
      BufferedReader reader;
      reader = new BufferedReader(new FileReader(sozluk));
      String satir = reader.readLine();
             while (satir!=null) 
                {
                if (satir.startsWith(str)==true)
                {
                    if (computeLevenshteinDistance(satir,str)==1)
                        { 
                         str=satir;break;
                        }
                }
                satir = reader.readLine();
                }
             reader.close();
        } catch(IOException e) {
      }
    return str;
    } 
    
    public static String enyakin_fiil_govdebul_4(String str) {
        
     String govde=str;
    
      for (int i=1; i<=str.length(); i++)
       {
        if (fiil_soylu_agac.search(str.substring(0,i))==true) govde=str.substring(0,i);
       }
    
    return govde;
    }  
    
    public static String enyakin_isim_govdebul_4(String str) {
        
     String govde=str;
    
      for (int i=1; i<=str.length(); i++)
       {
        if (isim_soylu_agac.search(str.substring(0,i))==true) govde=str.substring(0,i);
       }
    
    return govde;
    } 
    
    public static Boolean yapim_eki_kontrol(String str) {
        
    // Bazı yapım eki almış türemiş kelimeler sözlükte yer almadığı için bu kontrol yapılıyor. 
    // Bu eklerin listesi sözlüğün kapsamına göre arttırılabilir ya da azaltılabilir.
    // (Örn: kitaplık, dostça, çiçekçi vs.)
    
    Boolean yapim_eki = false;
    String[] yapim_ekler={"lıkçılık","likçilik","lukçuluk","lükçülük","sizlik","sızlık","cılık","cilik","culuk","cülük","çılık","çilik","çuluk","çülük","lıkçı","lılık","likçi","lilik","lukçu","luluk","lükçü","lülük","lık","lik","luk","lük","ca","ce","cı","ci","cu","cü","ça","çe","çı","çi","çu","çü"};
        for (String i:yapim_ekler)
        {
            if (str.endsWith(i)==true)
            {
              str=replaceLast(str,i,"");
              if (isim_soylu_agac.search(str)==true)  
                {
                  yapim_eki=true; break;
                }
            }
        } 
      return yapim_eki;
    }
    

    public static void govdeyazdir(String str4) {
    try {
     File govdeler = new File("govde.txt");
     if (!govdeler.exists()) {
            govdeler.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(govdeler,true);
        try (BufferedWriter bWriter = new BufferedWriter(fileWriter)) {
            bWriter.write(str4);
            bWriter.newLine();
            bWriter.flush();
        }
       } catch(IOException e) {
     }
    }  
    
    public static void etiketyazdir(String str1, String str2) {
    try {
     File govdeler = new File("etiket.txt");
     if (!govdeler.exists()) {
            govdeler.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(govdeler,true);
        try (BufferedWriter bWriter = new BufferedWriter(fileWriter)) {
            bWriter.write(str1);  bWriter.write(str2);
            bWriter.newLine();
            bWriter.flush();
        }
       } catch(IOException e) {
     }
    }  
    
    public static String turbul(String str) {
        
    String tur="(isim soylu-isim)*";    
    if (baglac_agac.search(str)==true) tur="(isim soylu-bağlaç)";
    else if (edat_agac.search(str)==true) tur="(isim soylu-edat)"; 
    else if (unlem_agac.search(str)==true) tur="(isim soylu-ünlem)";  
    else if (zamir_agac.search(str)==true) tur="(isim soylu-zamir)"; 
    else if (isim_agac.search(str)==true) tur="(isim soylu-isim)";  
   return tur;
  }
    
    public static int tur_no_bul(String str) {
        
    int turno=1;    
    if (baglac_agac.search(str)==true) turno=1;
    else if (edat_agac.search(str)==true) turno=1; 
    else if (unlem_agac.search(str)==true) turno=1;
    else if (zamir_agac.search(str)==true) turno=1;
    else if (sifat_agac.search(str)==true) turno=2;
    else if (isim_agac.search(str)==true) turno=3;
   return turno;
  }
}
