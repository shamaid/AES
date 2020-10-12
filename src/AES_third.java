import java.util.ArrayList;

public class AES_third
{
    private AES_first aes_first;
    private int matrixSize;

    public AES_third()
    {
        this.aes_first = new AES_first();
        this.matrixSize = this.aes_first.getMatrixSize();
    }

    /**
     * aes_third encrypt function.
     * Using aes_first_encrypt function, 3 time, 3 different keys.
     * @param plainText-text to encrypt.
     * @param key-keys to encrypt with.
     * @return the encrypted text.
     */
    public ArrayList<Byte[][]> aes_third_encrypt(ArrayList<Byte[][]> plainText, ArrayList<Byte[][]> key)
    {
        ArrayList<Byte[][]> ciperText;

        ciperText = aes_first.aes_first_encrypt(plainText, key.get(0));

        ciperText = aes_first.aes_first_encrypt(ciperText, key.get(1));

        ciperText = aes_first.aes_first_encrypt(ciperText, key.get(2));

        return ciperText;
    }

    /**
     * aes_third decrypt function.
     * Using aes_first_decrypt function, 3 time, 3 different keys.
     * @param ciperText-text to decrypt.
     * @param key-keys to decrypt with.
     * @return the decrypted text.
     */
    public ArrayList<Byte[][]> aes_third_decrypt(ArrayList<Byte[][]> ciperText, ArrayList<Byte[][]>key)
    {
        ArrayList<Byte[][]> plainText;

        plainText = aes_first.aes_first_decrypt(ciperText, key.get(2));

        plainText = aes_first.aes_first_decrypt(plainText, key.get(1));

        plainText = aes_first.aes_first_decrypt(plainText, key.get(0));

        return plainText;
    }

    /**
     * aes_third break function.
     * Using aes_first_encrypt function, 2 time, with key1 and with key2.
     * Find the key3.
     * @param plainText-the plain message.
     * @param ciperText-the ciper message.
     * @param key1-the first key.
     * @param key2-the second key.
     * @return the third key-in ArrayList of 2DByte.
     */
    public ArrayList<Byte[][]> aes_third_break(ArrayList<Byte[][]> plainText, ArrayList<Byte[][]> ciperText, Byte[][] key1, Byte[][] key2)
    {
        ArrayList<Byte[][]> stepToThirdKey;

        stepToThirdKey = aes_first.aes_first_encrypt(plainText, key1);

        stepToThirdKey = aes_first.aes_first_encrypt(stepToThirdKey, key2);

        stepToThirdKey = aes_third_breakShiftColumns(stepToThirdKey);

        return aes_third_breakAddRoundkey_ciper(stepToThirdKey, ciperText);
    }

    /**
     * Text break encrypting-the columns shifting.
     * @param inputText-text to shift his columns.
     * @return text shifted.
     */
    private ArrayList<Byte[][]> aes_third_breakShiftColumns(ArrayList<Byte[][]> inputText)
    {
        ArrayList<Byte[][]> outputText = new ArrayList<>();

        for (Byte[][] matrix : inputText)
        {
            outputText.add(matrixShiftColumns(matrix));
        }
        return outputText;
    }

    /**
     * Part of the text to encrypt breaking-the columns shifting.
     * @param matrix-part of text to shift columns up.
     * @return part of text shifted.
     */
    private Byte[][] matrixShiftColumns(Byte[][] matrix)
    {
        Byte[][] shiftedMatrix = new Byte[matrixSize][matrixSize];

        //col=0 -> no shifting.
        for (int row = 0; row < matrixSize; row++)
        {
            shiftedMatrix[row][0] = matrix[row][0];
        }
        //col=1/2/3 -> shift 1/2/3 up.
        return matrixShiftColumnsUp(matrix, shiftedMatrix);
    }

    /**
     * Part of the text to encrypt breaking-to shift columns up.
     * @param matrix-part of text to shift columns up.
     * @param shiftedMatrix-the matrix to shift to-first columns without change.
     * @return part of text shifted.
     */
    private Byte[][] matrixShiftColumnsUp(Byte[][] matrix, Byte[][] shiftedMatrix)
    {
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

    /**
     * Text break encrypting-the add round ciperText (key) function-using XOR.
     * @param plainText-one side of XOR-plain.
     * @param ciperText-second side of XOR-ciper.
     * @return the XOR between plain and ciper.
     */
    private ArrayList<Byte[][]> aes_third_breakAddRoundkey_ciper(ArrayList<Byte[][]> plainText, ArrayList<Byte[][]> ciperText)
    {
        ArrayList<Byte[][]> xor2DByteArray = new ArrayList<>();

        for (int index = 0; index < plainText.size(); index++)
        {
            Byte[][] matrix = new Byte[matrixSize][matrixSize];
            for (int row = 0; row < matrixSize; row++)
            {
                for (int col = 0; col < matrixSize; col++)
                {
                    matrix[row][col] = (byte)((plainText.get(index)[row][col]) ^ (ciperText.get(index)[row][col]));
                }
            }
            xor2DByteArray.add(matrix);
        }
        return xor2DByteArray;
    }
}
