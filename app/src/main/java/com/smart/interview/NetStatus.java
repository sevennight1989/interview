//package com.smart.interview;
//
//import android.content.Context;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//
//public class NetStatus {
//
//    public static int getNetStatus(Context context) {
//        int var1 = 0;
//        ConnectivityManager var2 = (ConnectivityManager) context.getApplicationContext().getSystemService("connectivity");
//        NetworkInfo var3 = var2.getActiveNetworkInfo();
//        if (var3 != null && var3.isConnectedOrConnecting()) {
//            String var4 = var3.getTypeName().toLowerCase();
//            if (var4.equals("wifi")) {
//                var1 = 1;
//            } else {
//                var1 = 2;
//                int var5 = var3.getSubtype();
//                switch (var5) {
//                    case 1:
//                    case 2:
//                    case 4:
//                    case 11:
//                    default:
//                        break;
//                    case 3:
//                        var1 = 3;
//                        break;
//                    case 5:
//                        var1 = 3;
//                        break;
//                    case 6:
//                        var1 = 3;
//                        break;
//                    case 7:
//                        var1 = 3;
//                        break;
//                    case 8:
//                        var1 = 3;
//                        break;
//                    case 9:
//                        var1 = 3;
//                        break;
//                    case 10:
//                        var1 = 3;
//                        break;
//                    case 12:
//                        var1 = 3;
//                        break;
//                    case 13:
//                        var1 = 4;
//                        break;
//                    case 14:
//                        var1 = 5;
//                }
//            }
//        }
//        return var1;
//
//    }
//}
