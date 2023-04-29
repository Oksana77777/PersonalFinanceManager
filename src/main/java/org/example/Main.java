//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.example;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    static Scanner scanner;
    static String[] products;
    static int[] prices;

    public Main() {
    }

    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
        XMLSettingsReader settings = new XMLSettingsReader(new File("shop.xml"));
        File loadFile = new File(settings.loadFile);
        File saveFile = new File(settings.saveFile);
        File logFile = new File(settings.logFile);
        Basket basket = createBasket(loadFile, settings.isLoad, settings.loadFormat);
        ClientLog log = new ClientLog();

        while (true) {
            showPrice();
            System.out.println("Выберите товар и количество через пробел или введите 'end' ");
            String input = scanner.nextLine();
            if ("end".equals(input)) {
                if (settings.isLog) {
                    log.exportAsCSV(logFile);
                }

                basket.printCart();
                return;
            }

            String[] parts = input.split(" ");
            int productNumber = Integer.parseInt(parts[0]) - 1;
            int productCount = Integer.parseInt(parts[1]);
            basket.addToCart(productNumber, productCount);
            if (settings.isLog) {
                log.log(productNumber, productCount);
            }

            if (settings.isSave) {
                switch (settings.saveFormat) {
                    case "json":
                        basket.saveJSON(saveFile);
                        break;
                    case "txt":
                        basket.saveTxt(saveFile);
                }
            }
        }
    }

    private static Basket createBasket(File loadFile, boolean isLoad, String loadFormat) {
        Basket basket;
        if (isLoad && loadFile.exists()) {
            Basket var10000;
            switch (loadFormat) {
                case "json":
                    var10000 = Basket.loadFromJSONFile(loadFile);
                    break;
                case "txt":
                    var10000 = Basket.loadFromTxtFile(loadFile);
                    break;
                default:
                    var10000 = new Basket(products, prices);
            }

            basket = var10000;
        } else {
            basket = new Basket(products, prices);
        }

        return basket;
    }

    public static void showPrice() {
        System.out.println("Список возможных товаров для покупки");

        for (int i = 0; i < products.length; ++i) {
            String var10001 = products[i];
            System.out.println(var10001 + " " + prices[i] + " руб/шт ");
        }

    }

    static {
        scanner = new Scanner(System.in);
        products = new String[]{"№1-Хлеб", "№2-Яблоки", "№3-Молоко", "№4-Колбаса", "№5-Курица"};
        prices = new int[]{50, 143, 70, 420, 329};
    }
}
