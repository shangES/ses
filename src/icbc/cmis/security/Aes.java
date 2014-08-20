/*
 * 创建日期 2004-11-22
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package icbc.cmis.security;

/**
 * @author ZJFH-yanb
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class Aes {
//    public enum KeySize {
//        Bits128, Bits192, Bits256 }; // key size, in bits, for construtor

    private int Nb; // block size in 32-bit words.  Always 4 for AES.  (128 bits).
    private int Nk; // key size in 32-bit words.  4, 6, 8.  (128, 192, 256 bits).
    private int Nr; // number of rounds. 10, 12, 14.

    private byte[] key; // the seed key. size will be 4 * keySize from ctor.
    private byte[][] Sbox =  // Substitution box
	        { // populate the Sbox matrix
				/*       0          1          2          3          4          5          6          7          8          9          a          b          c          d          e          f */
				/*0*/ { 
				(byte)0x63,(byte)0x7c,(byte)0x77,(byte)0x7b,(byte)0xf2,(byte)0x6b,(byte)0x6f,(byte)0xc5,(byte)0x30,(byte)0x01,(byte)0x67,(byte)0x2b,(byte)0xfe,(byte)0xd7,(byte)0xab,(byte)0x76 },
				/*1*/ {
				(byte)0xca,(byte)0x82,(byte)0xc9,(byte)0x7d,(byte)0xfa,(byte)0x59,(byte)0x47,(byte)0xf0,(byte)0xad,(byte)0xd4,(byte)0xa2,(byte)0xaf,(byte)0x9c,(byte)0xa4,(byte)0x72,(byte)0xc0 },
				/*2*/ {
				(byte)0xb7,(byte)0xfd,(byte)0x93,(byte)0x26,(byte)0x36,(byte)0x3f,(byte)0xf7,(byte)0xcc,(byte)0x34,(byte)0xa5,(byte)0xe5,(byte)0xf1,(byte)0x71,(byte)0xd8,(byte)0x31,(byte)0x15 },
				/*3*/ {
				(byte)0x04,(byte)0xc7,(byte)0x23,(byte)0xc3,(byte)0x18,(byte)0x96,(byte)0x05,(byte)0x9a,(byte)0x07,(byte)0x12,(byte)0x80,(byte)0xe2,(byte)0xeb,(byte)0x27,(byte)0xb2,(byte)0x75 },
				/*4*/ {
				(byte)0x09,(byte)0x83,(byte)0x2c,(byte)0x1a,(byte)0x1b,(byte)0x6e,(byte)0x5a,(byte)0xa0,(byte)0x52,(byte)0x3b,(byte)0xd6,(byte)0xb3,(byte)0x29,(byte)0xe3,(byte)0x2f,(byte)0x84 },
				/*5*/ {
				(byte)0x53,(byte)0xd1,(byte)0x00,(byte)0xed,(byte)0x20,(byte)0xfc,(byte)0xb1,(byte)0x5b,(byte)0x6a,(byte)0xcb,(byte)0xbe,(byte)0x39,(byte)0x4a,(byte)0x4c,(byte)0x58,(byte)0xcf },
				/*6*/ {
				(byte)0xd0,(byte)0xef,(byte)0xaa,(byte)0xfb,(byte)0x43,(byte)0x4d,(byte)0x33,(byte)0x85,(byte)0x45,(byte)0xf9,(byte)0x02,(byte)0x7f,(byte)0x50,(byte)0x3c,(byte)0x9f,(byte)0xa8 },
				/*7*/ {
				(byte)0x51,(byte)0xa3,(byte)0x40,(byte)0x8f,(byte)0x92,(byte)0x9d,(byte)0x38,(byte)0xf5,(byte)0xbc,(byte)0xb6,(byte)0xda,(byte)0x21,(byte)0x10,(byte)0xff,(byte)0xf3,(byte)0xd2 },
				/*8*/ {
				(byte)0xcd,(byte)0x0c,(byte)0x13,(byte)0xec,(byte)0x5f,(byte)0x97,(byte)0x44,(byte)0x17,(byte)0xc4,(byte)0xa7,(byte)0x7e,(byte)0x3d,(byte)0x64,(byte)0x5d,(byte)0x19,(byte)0x73 },
				/*9*/ {
				(byte)0x60,(byte)0x81,(byte)0x4f,(byte)0xdc,(byte)0x22,(byte)0x2a,(byte)0x90,(byte)0x88,(byte)0x46,(byte)0xee,(byte)0xb8,(byte)0x14,(byte)0xde,(byte)0x5e,(byte)0x0b,(byte)0xdb },
				/*a*/ {
				(byte)0xe0,(byte)0x32,(byte)0x3a,(byte)0x0a,(byte)0x49,(byte)0x06,(byte)0x24,(byte)0x5c,(byte)0xc2,(byte)0xd3,(byte)0xac,(byte)0x62,(byte)0x91,(byte)0x95,(byte)0xe4,(byte)0x79 },
				/*b*/ {
				(byte)0xe7,(byte)0xc8,(byte)0x37,(byte)0x6d,(byte)0x8d,(byte)0xd5,(byte)0x4e,(byte)0xa9,(byte)0x6c,(byte)0x56,(byte)0xf4,(byte)0xea,(byte)0x65,(byte)0x7a,(byte)0xae,(byte)0x08 },
				/*c*/ {
				(byte)0xba,(byte)0x78,(byte)0x25,(byte)0x2e,(byte)0x1c,(byte)0xa6,(byte)0xb4,(byte)0xc6,(byte)0xe8,(byte)0xdd,(byte)0x74,(byte)0x1f,(byte)0x4b,(byte)0xbd,(byte)0x8b,(byte)0x8a },
				/*d*/ {
				(byte)0x70,(byte)0x3e,(byte)0xb5,(byte)0x66,(byte)0x48,(byte)0x03,(byte)0xf6,(byte)0x0e,(byte)0x61,(byte)0x35,(byte)0x57,(byte)0xb9,(byte)0x86,(byte)0xc1,(byte)0x1d,(byte)0x9e },
				/*e*/ {
				(byte)0xe1,(byte)0xf8,(byte)0x98,(byte)0x11,(byte)0x69,(byte)0xd9,(byte)0x8e,(byte)0x94,(byte)0x9b,(byte)0x1e,(byte)0x87,(byte)0xe9,(byte)0xce,(byte)0x55,(byte)0x28,(byte)0xdf },
				/*f*/ {
				(byte)0x8c,(byte)0xa1,(byte)0x89,(byte)0x0d,(byte)0xbf,(byte)0xe6,(byte)0x42,(byte)0x68,(byte)0x41,(byte)0x99,(byte)0x2d,(byte)0x0f,(byte)0xb0,(byte)0x54,(byte)0xbb,(byte)0x16 }
			};
			
    private byte[][] iSbox = // inverse Substitution box
	        { // populate the iSbox matrix
				/*       0          1          2          3          4          5          6          7          8          9          a          b          c          d          e          f */
				/*0*/ { 
				(byte)0x52,(byte)0x09,(byte)0x6a,(byte)0xd5,(byte)0x30,(byte)0x36,(byte)0xa5,(byte)0x38,(byte)0xbf,(byte)0x40,(byte)0xa3,(byte)0x9e,(byte)0x81,(byte)0xf3,(byte)0xd7,(byte)0xfb },
				/*1*/ {
				(byte)0x7c,(byte)0xe3,(byte)0x39,(byte)0x82,(byte)0x9b,(byte)0x2f,(byte)0xff,(byte)0x87,(byte)0x34,(byte)0x8e,(byte)0x43,(byte)0x44,(byte)0xc4,(byte)0xde,(byte)0xe9,(byte)0xcb },
				/*2*/ {
				(byte)0x54,(byte)0x7b,(byte)0x94,(byte)0x32,(byte)0xa6,(byte)0xc2,(byte)0x23,(byte)0x3d,(byte)0xee,(byte)0x4c,(byte)0x95,(byte)0x0b,(byte)0x42,(byte)0xfa,(byte)0xc3,(byte)0x4e },
				/*3*/ {
				(byte)0x08,(byte)0x2e,(byte)0xa1,(byte)0x66,(byte)0x28,(byte)0xd9,(byte)0x24,(byte)0xb2,(byte)0x76,(byte)0x5b,(byte)0xa2,(byte)0x49,(byte)0x6d,(byte)0x8b,(byte)0xd1,(byte)0x25 },
				/*4*/ {
				(byte)0x72,(byte)0xf8,(byte)0xf6,(byte)0x64,(byte)0x86,(byte)0x68,(byte)0x98,(byte)0x16,(byte)0xd4,(byte)0xa4,(byte)0x5c,(byte)0xcc,(byte)0x5d,(byte)0x65,(byte)0xb6,(byte)0x92 },
				/*5*/ {
				(byte)0x6c,(byte)0x70,(byte)0x48,(byte)0x50,(byte)0xfd,(byte)0xed,(byte)0xb9,(byte)0xda,(byte)0x5e,(byte)0x15,(byte)0x46,(byte)0x57,(byte)0xa7,(byte)0x8d,(byte)0x9d,(byte)0x84 },
				/*6*/ {
				(byte)0x90,(byte)0xd8,(byte)0xab,(byte)0x00,(byte)0x8c,(byte)0xbc,(byte)0xd3,(byte)0x0a,(byte)0xf7,(byte)0xe4,(byte)0x58,(byte)0x05,(byte)0xb8,(byte)0xb3,(byte)0x45,(byte)0x06 },
				/*7*/ {
				(byte)0xd0,(byte)0x2c,(byte)0x1e,(byte)0x8f,(byte)0xca,(byte)0x3f,(byte)0x0f,(byte)0x02,(byte)0xc1,(byte)0xaf,(byte)0xbd,(byte)0x03,(byte)0x01,(byte)0x13,(byte)0x8a,(byte)0x6b },
				/*8*/ {
				(byte)0x3a,(byte)0x91,(byte)0x11,(byte)0x41,(byte)0x4f,(byte)0x67,(byte)0xdc,(byte)0xea,(byte)0x97,(byte)0xf2,(byte)0xcf,(byte)0xce,(byte)0xf0,(byte)0xb4,(byte)0xe6,(byte)0x73 },
				/*9*/ {
				(byte)0x96,(byte)0xac,(byte)0x74,(byte)0x22,(byte)0xe7,(byte)0xad,(byte)0x35,(byte)0x85,(byte)0xe2,(byte)0xf9,(byte)0x37,(byte)0xe8,(byte)0x1c,(byte)0x75,(byte)0xdf,(byte)0x6e },
				/*a*/ {
				(byte)0x47,(byte)0xf1,(byte)0x1a,(byte)0x71,(byte)0x1d,(byte)0x29,(byte)0xc5,(byte)0x89,(byte)0x6f,(byte)0xb7,(byte)0x62,(byte)0x0e,(byte)0xaa,(byte)0x18,(byte)0xbe,(byte)0x1b },
				/*b*/ {
				(byte)0xfc,(byte)0x56,(byte)0x3e,(byte)0x4b,(byte)0xc6,(byte)0xd2,(byte)0x79,(byte)0x20,(byte)0x9a,(byte)0xdb,(byte)0xc0,(byte)0xfe,(byte)0x78,(byte)0xcd,(byte)0x5a,(byte)0xf4 },
				/*c*/ {
				(byte)0x1f,(byte)0xdd,(byte)0xa8,(byte)0x33,(byte)0x88,(byte)0x07,(byte)0xc7,(byte)0x31,(byte)0xb1,(byte)0x12,(byte)0x10,(byte)0x59,(byte)0x27,(byte)0x80,(byte)0xec,(byte)0x5f },
				/*d*/ {
				(byte)0x60,(byte)0x51,(byte)0x7f,(byte)0xa9,(byte)0x19,(byte)0xb5,(byte)0x4a,(byte)0x0d,(byte)0x2d,(byte)0xe5,(byte)0x7a,(byte)0x9f,(byte)0x93,(byte)0xc9,(byte)0x9c,(byte)0xef },
				/*e*/ {
				(byte)0xa0,(byte)0xe0,(byte)0x3b,(byte)0x4d,(byte)0xae,(byte)0x2a,(byte)0xf5,(byte)0xb0,(byte)0xc8,(byte)0xeb,(byte)0xbb,(byte)0x3c,(byte)0x83,(byte)0x53,(byte)0x99,(byte)0x61 },
				/*f*/ {
				(byte)0x17,(byte)0x2b,(byte)0x04,(byte)0x7e,(byte)0xba,(byte)0x77,(byte)0xd6,(byte)0x26,(byte)0xe1,(byte)0x69,(byte)0x14,(byte)0x63,(byte)0x55,(byte)0x21,(byte)0x0c,(byte)0x7d }
			}; 
    private byte[][] w; // key schedule array. 
    private byte[][] Rcon = // Round constants.
	       { 
			{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00 },
			{(byte)0x01,(byte)0x00,(byte)0x00,(byte)0x00 },
			{(byte)0x02,(byte)0x00,(byte)0x00,(byte)0x00 },
			{(byte)0x04,(byte)0x00,(byte)0x00,(byte)0x00 },
			{(byte)0x08,(byte)0x00,(byte)0x00,(byte)0x00 },
			{(byte)0x10,(byte)0x00,(byte)0x00,(byte)0x00 },
			{(byte)0x20,(byte)0x00,(byte)0x00,(byte)0x00 },
			{(byte)0x40,(byte)0x00,(byte)0x00,(byte)0x00 },
			{(byte)0x80,(byte)0x00,(byte)0x00,(byte)0x00 },
			{(byte)0x1b,(byte)0x00,(byte)0x00,(byte)0x00 },
			{(byte)0x36,(byte)0x00,(byte)0x00,(byte)0x00 }
			};
    private byte[][] State; // State matrix

    public Aes(KeySize keySize, byte[] keyBytes) {
        SetNbNkNr(keySize);

        this.key = new byte[this.Nk * 4]; // 16, 24, 32 bytes
        CopyTo(keyBytes,this.key, 0);

//        BuildSbox();
//        BuildInvSbox();
//        BuildRcon();
        KeyExpansion(); // expand the seed key into a key schedule and store in w

    } // Aes constructor

    public byte[] Cipher(byte[] input ) // encipher 16-bit input
    {
        // state = input
        byte[] output = new byte[4 * Nb];
        this.State = new byte[4][Nb]; // always [4,4]
        for (int i = 0; i < (4 * Nb); ++i) {
            this.State[i % 4][i / 4] = input[i];
        }

        AddRoundKey(0);

        for (int round = 1; round <= (Nr - 1); ++round) // main round loop
            {
            SubBytes();
            ShiftRows();
            MixColumns();
            AddRoundKey(round);
        } // main round loop

        SubBytes();
        ShiftRows();
        AddRoundKey(Nr);

        // output = state
        for (int i = 0; i < (4 * Nb); ++i) {
            output[i] = this.State[i % 4][i / 4];
        }
        return output;

    } // Cipher()

    public byte[] InvCipher(byte[] input ) // decipher 16-bit input
    {
        // state = input
		byte[] output = new byte[4 * Nb];
        this.State = new byte[4][Nb]; // always [4,4]
        for (int i = 0; i < (4 * Nb); ++i) {
            this.State[i % 4][i / 4] = input[i];
        }

        AddRoundKey(Nr);

        for (int round = Nr - 1; round >= 1; --round) // main round loop
            {
            InvShiftRows();
            InvSubBytes();
            AddRoundKey(round);
            InvMixColumns();
        } // end main round loop for InvCipher

        InvShiftRows();
        InvSubBytes();
        AddRoundKey(0);

        // output = state
        for (int i = 0; i < (4 * Nb); ++i) {
            output[i] = this.State[i % 4][i / 4];
        }
		return output;

    } // InvCipher()

    private void SetNbNkNr(KeySize keySize) {
        this.Nb = 4; // block size always = 4 words = 16 bytes = 128 bits for AES

        if (keySize == KeySize.Bits128) {
            this.Nk = 4; // key size = 4 words = 16 bytes = 128 bits
            this.Nr = 10; // rounds for algorithm = 10
        } else if (keySize == KeySize.Bits192) {
            this.Nk = 6; // 6 words = 24 bytes = 192 bits
            this.Nr = 12;
        } else if (keySize == KeySize.Bits256) {
            this.Nk = 8; // 8 words = 32 bytes = 256 bits
            this.Nr = 14;
        }
    } // SetNbNkNr()

//    private void BuildSbox() {
//        this.Sbox = new byte
//
//    } // BuildSbox() 

//    private void BuildInvSbox() {
//        this.iSbox = new byte[16][16] 
//
//    } // BuildInvSbox()

//    private void BuildRcon() {
//        this.Rcon = new byte{ { 0x00, 0x00, 0x00, 0x00 }, {
//                0x01, 0x00, 0x00, 0x00 }, {
//                0x02, 0x00, 0x00, 0x00 }, {
//                0x04, 0x00, 0x00, 0x00 }, {
//                0x08, 0x00, 0x00, 0x00 }, {
//                0x10, 0x00, 0x00, 0x00 }, {
//                0x20, 0x00, 0x00, 0x00 }, {
//                0x40, 0x00, 0x00, 0x00 }, {
//                0x80, 0x00, 0x00, 0x00 }, {
//                0x1b, 0x00, 0x00, 0x00 }, {
//                0x36, 0x00, 0x00, 0x00 }
//        };
//    } // BuildRcon()

    private void AddRoundKey(int round) {

        for (int r = 0; r < 4; ++r) {
            for (int c = 0; c < 4; ++c) {
                this.State[r][c] = (byte) (this.State[r][c] ^ w[(round * 4) + c][r]);
            }
        }
    } // AddRoundKey()

    private void SubBytes() {
        for (int r = 0; r < 4; ++r) {
            for (int c = 0; c < 4; ++c) {
                this.State[r][c] = this.Sbox[(this.State[r][c] >> 4)][(this.State[r][c] & 0x0f)];
            }
        }
    } // SubBytes

    private void InvSubBytes() {
        for (int r = 0; r < 4; ++r) {
            for (int c = 0; c < 4; ++c) {
                this.State[r][c] = this.iSbox[(this.State[r][c] >> 4)][(this.State[r][c] & 0x0f)];
            }
        }
    } // InvSubBytes

    private void ShiftRows() {
        byte[][] temp = new byte[4][4];
        for (int r = 0; r < 4; ++r) // copy State into temp[]
            {
            for (int c = 0; c < 4; ++c) {
                temp[r][c] = this.State[r][c];
            }
        }

        for (int r = 1; r < 4; ++r) // shift temp into State
            {
            for (int c = 0; c < 4; ++c) {
                this.State[r][c] = temp[r][(c + r) % Nb];
            }
        }
    } // ShiftRows()

    private void InvShiftRows() {
        byte[][] temp = new byte[4][4];
        for (int r = 0; r < 4; ++r) // copy State into temp[]
            {
            for (int c = 0; c < 4; ++c) {
                temp[r][c] = this.State[r][c];
            }
        }
        for (int r = 1; r < 4; ++r) // shift temp into State
            {
            for (int c = 0; c < 4; ++c) {
                this.State[r][(c + r) % Nb] = temp[r][c];
            }
        }
    } // InvShiftRows()

    private void MixColumns() {
        byte[][] temp = new byte[4][4];
        for (int r = 0; r < 4; ++r) // copy State into temp[]
            {
            for (int c = 0; c < 4; ++c) {
                temp[r][c] = this.State[r][c];
            }
        }

        for (int c = 0; c < 4; ++c) {
            this.State[0][c] = (byte) (gfmultby02(temp[0][c]) ^ gfmultby03(temp[1][c]) ^ gfmultby01(temp[2][c]) ^ gfmultby01(temp[3][c]));
            this.State[1][c] = (byte) (gfmultby01(temp[0][c]) ^ gfmultby02(temp[1][c]) ^ gfmultby03(temp[2][c]) ^ gfmultby01(temp[3][c]));
            this.State[2][c] = (byte) (gfmultby01(temp[0][c]) ^ gfmultby01(temp[1][c]) ^ gfmultby02(temp[2][c]) ^ gfmultby03(temp[3][c]));
            this.State[3][c] = (byte) (gfmultby03(temp[0][c]) ^ gfmultby01(temp[1][c]) ^ gfmultby01(temp[2][c]) ^ gfmultby02(temp[3][c]));
        }
    } // MixColumns

    private void InvMixColumns() {
        byte[][] temp = new byte[4][4];
        for (int r = 0; r < 4; ++r) // copy State into temp[]
            {
            for (int c = 0; c < 4; ++c) {
                temp[r][c] = this.State[r][c];
            }
        }

        for (int c = 0; c < 4; ++c) {
            this.State[0][c] = (byte) (gfmultby0e(temp[0][c]) ^ gfmultby0b(temp[1][c]) ^ gfmultby0d(temp[2][c]) ^ gfmultby09(temp[3][c]));
            this.State[1][c] = (byte) (gfmultby09(temp[0][c]) ^ gfmultby0e(temp[1][c]) ^ gfmultby0b(temp[2][c]) ^ gfmultby0d(temp[3][c]));
            this.State[2][c] = (byte) (gfmultby0d(temp[0][c]) ^ gfmultby09(temp[1][c]) ^ gfmultby0e(temp[2][c]) ^ gfmultby0b(temp[3][c]));
            this.State[3][c] = (byte) (gfmultby0b(temp[0][c]) ^ gfmultby0d(temp[1][c]) ^ gfmultby09(temp[2][c]) ^ gfmultby0e(temp[3][c]));
        }
    } // InvMixColumns

    private static byte gfmultby01(byte b) {
        return b;
    }

    private static byte gfmultby02(byte b) {
        if (b < 0x80)
            return (byte)  (b << 1);
        else
            return (byte) ( (b << 1) ^  (0x1b));
    }

    private static byte gfmultby03(byte b) {
        return (byte) (gfmultby02(b) ^ b);
    }

    private static byte gfmultby09(byte b) {
        return (byte) (gfmultby02(gfmultby02(gfmultby02(b))) ^ b);
    }

    private static byte gfmultby0b(byte b) {
        return (byte) (gfmultby02(gfmultby02(gfmultby02(b))) ^ gfmultby02(b) ^ b);
    }

    private static byte gfmultby0d(byte b) {
        return (byte) (gfmultby02(gfmultby02(gfmultby02(b))) ^ gfmultby02(gfmultby02(b)) ^  (b));
    }

    private static byte gfmultby0e(byte b) {
        return (byte) (gfmultby02(gfmultby02(gfmultby02(b))) ^ gfmultby02(gfmultby02(b)) ^ gfmultby02(b));
    }

    private void KeyExpansion() {
        this.w = new byte[Nb * (Nr + 1)][4]; // 4 columns of bytes corresponds to a word

        for (int row = 0; row < Nk; ++row) {
            this.w[row][0] = this.key[4 * row];
            this.w[row][1] = this.key[4 * row + 1];
            this.w[row][2] = this.key[4 * row + 2];
            this.w[row][3] = this.key[4 * row + 3];
        }

        byte[] temp = new byte[4];

        for (int row = Nk; row < Nb * (Nr + 1); ++row) {
            temp[0] = this.w[row - 1][0];
            temp[1] = this.w[row - 1][1];
            temp[2] = this.w[row - 1][2];
            temp[3] = this.w[row - 1][3];

            if (row % Nk == 0) {
                temp = SubWord(RotWord(temp));

                temp[0] = (byte) (temp[0] ^ this.Rcon[row / Nk][0]);
                temp[1] = (byte) (temp[1] ^ this.Rcon[row / Nk][1]);
                temp[2] = (byte) (temp[2] ^ this.Rcon[row / Nk][2]);
                temp[3] = (byte) (temp[3] ^ this.Rcon[row / Nk][3]);
            } else if (Nk > 6 && (row % Nk == 4)) {
                temp = SubWord(temp);
            }

            // w[row] = w[row-Nk] xor temp
            this.w[row][0] = (byte) (this.w[row - Nk][0] ^ temp[0]);
            this.w[row][1] = (byte) (this.w[row - Nk][1] ^ temp[1]);
            this.w[row][2] = (byte) (this.w[row - Nk][2] ^ temp[2]);
            this.w[row][3] = (byte) (this.w[row - Nk][3] ^ temp[3]);

        } // for loop
    } // KeyExpansion()

    private byte[] SubWord(byte[] word) {
        byte[] result = new byte[4];
        result[0] = this.Sbox[word[0] >> 4][word[0] & 0x0f];
        result[1] = this.Sbox[word[1] >> 4][word[1] & 0x0f];
        result[2] = this.Sbox[word[2] >> 4][word[2] & 0x0f];
        result[3] = this.Sbox[word[3] >> 4][word[3] & 0x0f];
        return result;
    }

    private byte[] RotWord(byte[] word) {
        byte[] result = new byte[4];
        result[0] = word[1];
        result[1] = word[2];
        result[2] = word[3];
        result[3] = word[0];
        return result;
    }

//    public void Dump() {
//        Console.WriteLine("Nb = " + Nb + " Nk = " + Nk + " Nr = " + Nr);
//        Console.WriteLine("\nThe key is \n" + DumpKey());
//        Console.WriteLine("\nThe Sbox is \n" + DumpTwoByTwo(Sbox));
//        Console.WriteLine("\nThe w array is \n" + DumpTwoByTwo(w));
//        Console.WriteLine("\nThe State array is \n" + DumpTwoByTwo(State));
//    }
//
//    public string DumpKey() {
//        string s = "";
//        for (int i = 0; i < key.Length; ++i)
//            s += key[i].ToString("x2") + " ";
//        return s;
//    }
//
//    public string DumpTwoByTwo(byte[,] a) {
//        string s = "";
//        for (int r = 0; r < a.GetLength(0); ++r) {
//            s += "[" + r + "]" + " ";
//            for (int c = 0; c < a.GetLength(1); ++c) {
//                s += a[r][c].ToString("x2") + " ";
//            }
//            s += "\n";
//        }
//        return s;
//    }
    
    private void CopyTo(byte[] src, byte[] dest, int index){
    	for(int i=index;i<dest.length && i-index<src.length;i++)
		  dest[i] = src[i-index];
    }
}
