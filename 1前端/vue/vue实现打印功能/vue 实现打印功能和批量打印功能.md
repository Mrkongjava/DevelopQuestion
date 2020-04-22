## vue实现打印功能

1. 第一步：安装打印机的驱动，安装对应打印机驱动即可

2. 首先安装打印控件（install_lodop64.exe） 文件夹有对应安装包

3. 代码实现

   ```
   1、引入打印控件
   import { getLodop } from '@/utils/LodopFuncs'
   
   2、这里实现的是批量打印，因此将预览代码注释掉；若是单个打印只需要for循环中的代码即可，并且将注释掉的打印预览代码放开
   batchPrint(datas){
           const mythis = this;
   
           if (datas.length == 0){
             this.$message.warning('请选择需要打印的订单！');
           }else{
             var LODOP = getLodop();
             for (let i = 0; i < datas.length; i++) {
               var data = datas[i];
               
               // var cardId = data["CARD_NBR"];
               //身份证中间10位用*代替
               // cardId = cardId.replace(cardId.substring(4, 14), "**********");
               // var str = "<img src='../../views/orderModel/order/arrivePay2.jpg' border='0' />";
   
               LODOP.PRINT_INITA(0, 0, "99mm", "150mm", "现付模板");
               LODOP.ADD_PRINT_SETUP_BKIMG("<img src='http://img-test.yeba.im//tmp/wxa64ed99be1041d38.o6zAJsxh-HCmjaYX424QqrwfKNXI.StoPRBmEASnP702026307a095a0d693e49bd9a52a184.jpg' border='0' />");
               LODOP.SET_SHOW_MODE("BKIMG_LEFT", 0);
               LODOP.SET_SHOW_MODE("BKIMG_TOP", 5);
               LODOP.SET_SHOW_MODE("BKIMG_WIDTH", 336);
               LODOP.SET_SHOW_MODE("BKIMG_HEIGHT", 538);
               LODOP.SET_SHOW_MODE("BKIMG_IN_PREVIEW", true);
               LODOP.SET_SHOW_MODE("BKIMG_PRINT", true);
               LODOP.SET_PRINT_PAGESIZE(1, "99mm", "150mm", "");
               LODOP.ADD_PRINT_TEXT(33,8,47,24, data.receiveCity);
               LODOP.SET_PRINT_STYLEA(0,"FontName","黑体");
               LODOP.SET_PRINT_STYLEA(0, "FontSize", 13);
               LODOP.SET_PRINT_STYLEA(0, "Bold", 1);
   
               // LODOP.ADD_PRINT_TEXT(33,48,57,24,data["T_CUST_POST"]);
               // LODOP.SET_PRINT_STYLEA(0,"FontName","黑体");
               // LODOP.SET_PRINT_STYLEA(0, "FontSize", 13);
               // LODOP.SET_PRINT_STYLEA(0, "Bold", 1);
   
               LODOP.ADD_PRINT_TEXT(65,35,164,20, data.sender);
               LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
               LODOP.ADD_PRINT_TEXT(86, 9, 190, 16, data.senderAddr);
               LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
               LODOP.ADD_PRINT_TEXT(102, 65, 160, 16, data.senderPhone);
               LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
               LODOP.ADD_PRINT_TEXT(123, 42, 89, 16, data.receiver);
               LODOP.SET_PRINT_STYLEA(0, "FontSize", 11);
               LODOP.ADD_PRINT_TEXT(123, 158, 94, 16, data.recPhone);
               LODOP.SET_PRINT_STYLEA(0, "FontSize", 11);
               LODOP.ADD_PRINT_TEXT(123, 260, 92, 16, data.recPhone);
               LODOP.SET_PRINT_STYLEA(0, "FontSize", 11);
               LODOP.ADD_PRINT_TEXT(142, 42, 314, 50, data.receiveAddr);
               LODOP.SET_PRINT_STYLEA(0, "FontSize", 11);
               LODOP.SET_PRINT_STYLEA(0, "Bold", 1);
               LODOP.ADD_PRINT_TEXT(391, 40, 130, 16, data.sender);
               LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
               LODOP.ADD_PRINT_TEXT(401, 9, 161, 20, data.senderAddr);
               LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
               LODOP.ADD_PRINT_BARCODE(335, 14, 218, 40, "Code39", data.expressNbr);
               LODOP.ADD_PRINT_BARCODE(18, 232, 181, 44, "Code39", data.expressNbr);
               LODOP.ADD_PRINT_TEXT(431, 61, 160, 16, data.senderPhone);
               LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
               LODOP.ADD_PRINT_TEXT(394, 204, 72, 16, data.receiver);
               LODOP.SET_PRINT_STYLEA(0, "FontSize", 8)
               LODOP.ADD_PRINT_TEXT(394, 280, 80, 16, data.recPhone);
               LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
               LODOP.ADD_PRINT_TEXT(412, 178, 182, 38, data.receiveAddr);
               LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
               // LODOP.ADD_PRINT_TEXT(470, 5, 80, 16, "身份证号：");
               // LODOP.ADD_PRINT_TEXT(489, 4, 152, 19, cardId);
               LODOP.ADD_PRINT_TEXT(273, 112, 152, 19, "18");
               LODOP.ADD_PRINT_TEXT(310, 300, 152, 19, "0.1Kg");
               LODOP.ADD_PRINT_TEXT(230, 40, 152, 19, "0.1Kg");
               LODOP.PRINT();
   
               //是否预览
               // LODOP.PREVIEW()
   
               //打印完成后，写入进度表与更新订单表
               mythis.updateOrderPrint(data.id);
             }
             mythis.crud.refresh()
           }
         }
   ```

4. 水电费

5. 水电费

6. 水电费

