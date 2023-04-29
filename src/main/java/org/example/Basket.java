package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.Arrays;

public class Basket implements Serializable {
    private static final long serialVersionUID = 1L;
    private String[] goods;
    private int[] prices;
    private int[] quantities;

    public Basket() {
    }

    public Basket(String[] goods, int[] prices) {
        this.goods = goods;
        this.prices = prices;
        this.quantities = new int[goods.length];
    }

    public void addToCart(int productNum, int amount) {
        int[] var10000 = this.quantities;
        var10000[productNum] += amount;
    }

    public void printCart() {
        int totalPrice = 0;
        System.out.println("Список покупок:");

        for (int i = 0; i < this.goods.length; ++i) {
            if (this.quantities[i] > 0) {
                int currentPrice = this.prices[i] * this.quantities[i];
                totalPrice += currentPrice;
                System.out.printf("%15s%4d руб/шт%4d шт ➞%4d руб%n", this.goods[i], this.prices[i], this.quantities[i], currentPrice);
            }
        }

        System.out.printf("Итого: %d рублей", totalPrice);
    }

    public void saveTxt(File textFile) throws FileNotFoundException {
        PrintWriter out = new PrintWriter(textFile);

        try {
            out.println(String.join(" ", this.goods));
            out.println(String.join(" ", (CharSequence[]) Arrays.stream(this.prices).mapToObj(String::valueOf).toArray((x$0) -> {
                return new String[x$0];
            })));
            out.println(String.join(" ", (CharSequence[]) Arrays.stream(this.quantities).mapToObj(String::valueOf).toArray((x$0) -> {
                return new String[x$0];
            })));
        } catch (Throwable var6) {
            try {
                out.close();
            } catch (Throwable var5) {
                var6.addSuppressed(var5);
            }

            throw var6;
        }

        out.close();
    }

    public static Basket loadFromTxtFile(File textFile) {
        Basket basket = new Basket();

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(textFile));

            try {
                String goodsStr = bufferedReader.readLine();
                String pricesStr = bufferedReader.readLine();
                String quantitiesStr = bufferedReader.readLine();
                basket.goods = goodsStr.split(" ");
                basket.prices = Arrays.stream(pricesStr.split(" ")).map(Integer::parseInt).mapToInt(Integer::intValue).toArray();
                basket.quantities = Arrays.stream(quantitiesStr.split(" ")).map(Integer::parseInt).mapToInt(Integer::intValue).toArray();
            } catch (Throwable var7) {
                try {
                    bufferedReader.close();
                } catch (Throwable var6) {
                    var7.addSuppressed(var6);
                }

                throw var7;
            }

            bufferedReader.close();
            return basket;
        } catch (IOException var8) {
            throw new RuntimeException(var8);
        }
    }

    public void saveBin(File file) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));

            try {
                oos.writeObject(this);
            } catch (Throwable var6) {
                try {
                    oos.close();
                } catch (Throwable var5) {
                    var6.addSuppressed(var5);
                }

                throw var6;
            }

            oos.close();
        } catch (IOException var7) {
            throw new RuntimeException(var7);
        }
    }

    public static Basket loadFromBinFile(File file) {
        Basket basket = null;

        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));

            try {
                basket = (Basket) ois.readObject();
            } catch (Throwable var6) {
                try {
                    ois.close();
                } catch (Throwable var5) {
                    var6.addSuppressed(var5);
                }

                throw var6;
            }

            ois.close();
            return basket;
        } catch (ClassNotFoundException | IOException var7) {
            throw new RuntimeException(var7);
        }
    }

    public void saveJSON(File file) {
        try {
            PrintWriter writer = new PrintWriter(file);

            try {
                Gson gson = (new GsonBuilder()).setPrettyPrinting().create();
                String json = gson.toJson(this);
                writer.print(json);
            } catch (Throwable var6) {
                try {
                    writer.close();
                } catch (Throwable var5) {
                    var6.addSuppressed(var5);
                }

                throw var6;
            }

            writer.close();
        } catch (IOException var7) {
            throw new RuntimeException(var7);
        }
    }

    public static Basket loadFromJSONFile(File file) {
        Basket basket = null;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            try {
                StringBuilder builder = new StringBuilder();
                String line = null;

                while (true) {
                    if ((line = reader.readLine()) == null) {
                        Gson gson = new Gson();
                        basket = (Basket) gson.fromJson(builder.toString(), Basket.class);
                        break;
                    }

                    builder.append(line);
                }
            } catch (Throwable var7) {
                try {
                    reader.close();
                } catch (Throwable var6) {
                    var7.addSuppressed(var6);
                }

                throw var7;
            }

            reader.close();
            return basket;
        } catch (IOException var8) {
            throw new RuntimeException(var8);
        }
    }
}

