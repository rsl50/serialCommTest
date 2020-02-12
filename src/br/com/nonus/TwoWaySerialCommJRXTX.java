package br.com.nonus;

import org.openmuc.jrxtx.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static org.openmuc.jrxtx.SerialPortBuilder.getSerialPortNames;
import static org.openmuc.jrxtx.SerialPortBuilder.newBuilder;

public class TwoWaySerialCommJRXTX
{
    SerialPort serialPort = null;
    InputStream in;
    OutputStream out;

    public TwoWaySerialCommJRXTX()
    {
        super();
    }

    void connect ( String portName ) throws Exception
    {
        //String[] serialPorts = getSerialPortNames();
        serialPort = newBuilder(portName)
                    .setBaudRate(19200)
                    .setDataBits(DataBits.DATABITS_8)
                    .setStopBits(StopBits.STOPBITS_1)
                    .setParity(Parity.NONE)
                    .setFlowControl(FlowControl.NONE)
                    .build();

        //CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
        //if ( portIdentifier.isCurrentlyOwned() )
        //{
            //System.out.println("Error: Port is currently in use");
        //}
        //else
        //{
            //CommPort commPort = portIdentifier.open(this.getClass().getName(),2000);

            if ( serialPort instanceof SerialPort )
            {
                //SerialPort serialPort = null;
                //serialPort.setSerialPortParams(19200,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);

                in = serialPort.getInputStream();
                out = serialPort.getOutputStream();

                (new Thread(new SerialReader(in))).start();
                (new Thread(new SerialWriter(out))).start();

            }
            else
            {
                System.out.println("Error: Only serial ports are handled by this example.");
            }
        //}
    }

    /** */
    public static class SerialReader implements Runnable
    {
        InputStream in;

        public SerialReader ( InputStream in )
        {
            this.in = in;
        }

        public void run ()
        {
            byte[] buffer = new byte[1024];
            int len = -1;
            try
            {
                while ( ( len = this.in.read(buffer)) > -1 )
                {
                    System.out.print(new String(buffer,0,len));
                }
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }
        }
    }

    /** */
    public static class SerialWriter implements Runnable
    {
        OutputStream out;

        public SerialWriter ( OutputStream out )
        {
            this.out = out;
        }

        public void run ()
        {
            try
            {
                int c = 0;
                while ( ( c = System.in.read()) > -1 )
                {
                    this.out.write(c);
                }
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }
        }
    }
}
