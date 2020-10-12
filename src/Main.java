import sun.security.util.BitArray;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        //Path keyPath1 = Paths.get("C:\\Users\\USER\\Desktop\\לימודים\\'שנה ג\\'סמסטר ב\\אבטחת מחשבים\\עבודות\\עבודה 1\\test files\\key_long");
        //Path inputPath1 = Paths.get("C:\\Users\\USER\\Desktop\\לימודים\\'שנה ג\\'סמסטר ב\\אבטחת מחשבים\\עבודות\\עבודה 1\\test files\\cipher_long");

        //String command = "-e";

        //User command is to encrypt or decrypt:
        if(args[0].equals("-e") || args[0].equals("-d"))
        //if(command.equals("-e") || command.equals("-d"))
        {
            //Get key path and input path inorder to read their bytes:

            //args[1] it's "-k"
            //args[2] it's <path-to-key-file>
            Path keyPath = Paths.get(args[2]);
            //Path keyPath = Paths.get("C:\\Users\\USER\\Desktop\\test files\\key_short");

            //args[3] it's "-i"
            //args[4] it's <path-to-input-file>
            Path inputPath = Paths.get(args[4]);
            //Path inputPath = Paths.get("C:\\Users\\USER\\Desktop\\test files\\message_short");

            //Get the 1D byte array of the files from the path:
            byte[] key1DByteArray = Files.readAllBytes(keyPath);
            byte[] input1DByteArray = Files.readAllBytes(inputPath);


            //Split the 1D byte array to ArrayList of 2D byte array-4*4 block=128 bit each block:
            ArrayList<Byte[][]> keyList2DByteArray = splitByteArrayIntoBlocksArrayList(key1DByteArray);
            ArrayList<Byte[][]> inputList2DByteArray = splitByteArrayIntoBlocksArrayList(input1DByteArray);

            AES_third aes_third = new AES_third();

            ArrayList<Byte[][]> outputList2DByteArray = new ArrayList<>();

            //User command is to encrypt:
            //if(command.equals("-e"))
            if(args[0].equals("-e"))
            {
                outputList2DByteArray = aes_third.aes_third_encrypt(inputList2DByteArray, keyList2DByteArray);
            }
            //User command is to decrypt:
            //else
            if(args[0].equals("-d"))
            {
                outputList2DByteArray = aes_third.aes_third_decrypt(inputList2DByteArray, keyList2DByteArray);
            }

            byte[] output1DByteArray = reverse_SplitByteArrayIntoBlocksArrayList(outputList2DByteArray);

            //Open File to write the output 1D byte array to:

            //args[5] it's "-o"
            //args[6] it's <path-to-output-file>
            FileOutputStream fos = new FileOutputStream(args[6]);
            //FileOutputStream fos = new FileOutputStream("C:\\Users\\USER\\Desktop\\test files\\ciperOut.txt");
            fos.write(output1DByteArray);
            fos.close();
        }

        //User command is to break the algorithm:
        else if(args[0].equals("-b"))
        {
            //Get message path and ciper path inorder to read their bytes:

            //args[1] it's "-m"
            //args[2] it's <path-to-message>
            Path messagePath = Paths.get(args[2]);

            //args[3] it's "-c"
            //args[4] it's <path-to-ciper>
            Path ciperPath = Paths.get(args[4]);

            //Get the 1D byte array of the files from the path:
            byte[] message1DByteArray = Files.readAllBytes(messagePath);
            byte[] ciper1DByteArray = Files.readAllBytes(ciperPath);

            //Split the 1D byte array to ArrayList of 2D byte array-4*4 block=128 bit each block:
            ArrayList<Byte[][]> messageList2DByteArray = splitByteArrayIntoBlocksArrayList(message1DByteArray);
            ArrayList<Byte[][]> ciperList2DByteArray = splitByteArrayIntoBlocksArrayList(ciper1DByteArray);

            AES_third aes_third = new AES_third();

            int keySize = 16;
            byte [] key1 = randomKeys(keySize);
            byte [] key2 = randomKeys(keySize);

            Byte[][] key1_2DByte =splitByteArrayIntoBlocksArrayList(key1).get(0);
            Byte[][] key2_2DByte = splitByteArrayIntoBlocksArrayList(key2).get(0);
            //NOTE: key1 and key2 need to be different.

            ArrayList<Byte[][]> key3_2DByteArray = aes_third.aes_third_break(messageList2DByteArray, ciperList2DByteArray, key1_2DByte, key2_2DByte);

            byte[] key3 = reverse_SplitByteArrayIntoBlocksArrayList(key3_2DByteArray);

            //TODO-Combine all 3 keys together
            //NOTE: all 3 keys need to be different.


            byte[] all3Keys = CombineAll3keys(key1,key2,key3);

            //Open File to write the output 1D byte array to:

            //args[5] it's "-o"
            //args[6] it's <path-to-output-file>
            FileOutputStream fos = new FileOutputStream(args[6]);
            fos.write(all3Keys);
            fos.close();
        }









//        byte a;
//        byte b;
//        byte c;
//
//        a = 0;
//        b = 0;
//        c = (byte) (a^b);
//        System.out.println(+ a + "^" + b + "=" + c);
//
//        a = 0;
//        b = 1;
//        c = (byte) (a^b);
//        System.out.println(+ a + "^" + b + "=" + c);
//
//        a = 1;
//        b = 0;
//        c = (byte) (a^b);
//        System.out.println(+ a + "^" + b + "=" + c);
//
//        a = 1;
//        b = 1;
//        c = (byte) (a^b);
//        System.out.println(+ a + "^" + b + "=" + c);
//
//        System.out.println();
//
//        int matrixSize = 4;
//        int[][] matrix = new int[matrixSize][matrixSize];
//        int count = 0;
//
//        for (int row = 0; row < matrixSize; row++)
//        {
//            for (int col = 0; col < matrixSize; col++)
//            {
//                matrix[row][col] = count;
//                count++;
//            }
//        }
//
//        System.out.println("Origin Matrix");
//        print2DMatrix(matrix, matrixSize);
//        System.out.println();
//
//        int[][] shiftedMatrix;
//
//        shiftedMatrix = shiftColumns(matrix, "encrypt");
//
//        System.out.println("Shifted Matrix");
//        print2DMatrix(shiftedMatrix, matrixSize);
//        System.out.println();
//
//        shiftedMatrix = shiftColumns(shiftedMatrix, "decrypt");
//
//        System.out.println("Origin matrix-Shifted Shifted Matrix");
//        print2DMatrix(shiftedMatrix, matrixSize);
//        System.out.println();
    }

    private static byte[] CombineAll3keys( byte[] key1 , byte [] key2 , byte[] key3){
        byte[] all3Keys = new byte[key1.length*3];

        for (int index = 0; index <all3Keys.length ; index++) {
            if(index<key1.length){
                all3Keys[index]=key1[index];
            }
            else if(index<key1.length*2){
                all3Keys[index]=key2[index];
            }
            else if(index<key1.length*3){
                all3Keys[index]=key3[index];
            }
        }
        return all3Keys;
    }

    private static byte[] randomKeys(int keySize) {

        //int size = keySize/8;
        //size = (int) Math.sqrt(size);

        byte[] key = new byte[keySize];

        Random random = new Random();
        random.nextBytes(key);

        return key;

    }

    private static byte[] reverse_SplitByteArrayIntoBlocksArrayList(ArrayList<Byte[][]> messageInBlocks) {

        Byte [] message = new Byte[16*messageInBlocks.size()];

        int index = 0;
        for (int numOfBlocks = 0; index < message.length ; numOfBlocks++) {

            for (int row = 0; row < 4; row++) {

                for (int col = 0; col < 4; col++) {

                    message[index]=messageInBlocks.get(numOfBlocks)[col][row];
                    index++;
                }
            }
        }

        byte [] messageByteArray = new byte[message.length];
        for (int i = 0; i <message.length ; i++) {
            messageByteArray[i] = message[i];
        }

        return messageByteArray;

    }

    private static ArrayList<Byte[][]> splitByteArrayIntoBlocksArrayList(byte[] message) {

        //check how many blocks
        int amountOfBlocks = message.length / 16;
        ArrayList<Byte[][]> messageSplitToBlocks = new ArrayList();

        //int index=0;
        for (int i = 0; i < amountOfBlocks ; i++) {
            messageSplitToBlocks.add(new Byte[4][4]);
        }

        int counterOfBlocks = -1;
        for (int index = 0; index < message.length;) {

            counterOfBlocks++;

            for (int row = 0; row < 4; row++) {

                for (int col = 0; col < 4; col++) {

                    messageSplitToBlocks.get(counterOfBlocks)[col][row] = message[index];
                    index++;
                }

            }
        }
        return messageSplitToBlocks;
    }


    private static int[][] shiftColumns(int[][] matrix, String action)
    {
        int matrixSize = 4;

        int[][] shiftedMatrix = new int[matrixSize][matrixSize];

        //col=0 -> no shifting.
        for (int row = 0; row < matrixSize; row++)
        {
            shiftedMatrix[row][0] = matrix[row][0];
        }

        if (action.equals("encrypt"))
        {
            //col=1/2/3 -> shift 1/2/3 up.
            return matrixShiftColumnsUp(matrix, shiftedMatrix);
        }
        //else (action.equals("decrypt"))
        //col=1/2/3 -> shift 1/2/3 down-the revers shifting.
        return matrixShiftColumnsDown(matrix, shiftedMatrix);
    }

    private static int[][] matrixShiftColumnsDown(int[][] matrix, int[][] shiftedMatrix)
    {
        int matrixSize = 4;

        for (int col = 1; col < matrixSize; col++)
        {
            int matrixCol = 3;
            for (int row = matrixSize - 1; row >= 0; row--)
            {
                if (row > col - 1)
                {
                    shiftedMatrix[row][col] = matrix[row - col][col];
                }
                else
                {
                    shiftedMatrix[row][col] = matrix[matrixCol][col];
                    matrixCol--;
                }
            }
        }
        return shiftedMatrix;
    }

    private static int[][] matrixShiftColumnsUp(int[][] matrix, int[][] shiftedMatrix)
    {
        int matrixSize = 4;

        //col=1 -> shift 1 up.
        for (int col = 1; col < matrixSize; col++)
        {
            int matrixCol = 0;
            for (int row = 0; row < matrixSize; row++)
            {
                if (row + col < matrixSize)
                {
                    shiftedMatrix[row][col] = matrix[row + col][col];
                }
                else
                {
                    shiftedMatrix[row][col] = matrix[matrixCol][col];
                    matrixCol++;
                }
            }
        }
        return shiftedMatrix;
    }

    private static void print2DMatrix(int[][] matrix, int matrixSize)
    {
        for (int row = 0; row < matrixSize; row++)
        {
            for (int col = 0; col < matrixSize; col++)
            {
                System.out.print(matrix[row][col] + ",");
            }
            System.out.println();
        }
    }
}
