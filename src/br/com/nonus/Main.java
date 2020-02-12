package br.com.nonus;

public class Main {

    public static void main(String[] args)  {
        try
        {
            (new TwoWaySerialComm()).connect("COM1");
            //(new TwoWaySerialCommJRXTX()).connect("COM1");
        }
        catch ( Exception e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


}
