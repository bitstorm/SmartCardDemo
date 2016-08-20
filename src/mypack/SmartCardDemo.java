package mypack;

import java.util.List;

import javax.smartcardio.Card;
import javax.smartcardio.CardChannel;
import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;
import javax.smartcardio.TerminalFactory;

public class SmartCardDemo {
	public static final byte[] CMD_SELECT_MF = { (byte) 0x00, (byte) 0xA4, (byte) 0x00, (byte) 0x0C, (byte) 0x02, (byte) 0x3F,
			(byte) 0x00 };

	public static final byte[] CMD_SELECT_DF1 = { (byte) 0x00, (byte) 0xA4, (byte) 0x00, (byte) 0x0C, (byte) 0x02, (byte) 0x11,
			(byte) 0x00 };

	public static final byte[] CMD_SELECT_PERSONAL_FILE = { (byte) 0x00, (byte) 0xA4, (byte) 0x00, (byte) 0x0C, (byte) 0x02,
			(byte) 0x11, (byte) 0x02 };

	public static final byte[] CMD_READ_BINARY = { (byte) 0x00, (byte) 0xB0, (byte) 0x00, (byte) 0x00, (byte) 0x00 };

	@SuppressWarnings("restriction")
	public static void main(String[] args) throws CardException {
		TerminalFactory factory = TerminalFactory.getDefault();
		List<CardTerminal> terminals = factory.terminals().list();
		System.out.println("Terminals: " + terminals);

		// Use the first terminal
		CardTerminal terminal = terminals.get(0);

		System.out.println(terminal.getName());
		System.out.println(terminal.isCardPresent());

		Card card = terminal.connect("*");

		CardChannel channel = card.getBasicChannel();

		// Send Select Applet command
		// byte[] aid = {(byte)0xA0, 0x00, 0x00, 0x00, 0x62, 0x03, 0x01, 0x0C,
		// 0x06, 0x01};
		// ResponseAPDU answer = channel.transmit(new CommandAPDU(0x00, 0xA4,
		// 0x04, 0x00, aid));
		// System.out.println("answer: " + answer.toString());

		ResponseAPDU answer = channel.transmit(new CommandAPDU(CMD_SELECT_MF));
		System.out.println("answer: " + answer.toString());

		answer = channel.transmit(new CommandAPDU(CMD_SELECT_DF1));
		System.out.println("answer: " + answer.toString());

		answer = channel.transmit(new CommandAPDU(CMD_SELECT_PERSONAL_FILE));
		System.out.println("answer: " + answer.toString());

		answer = channel.transmit(new CommandAPDU(CMD_READ_BINARY));
		System.out.println("answer: " + new String(answer.getData()));
		// Send test command
		// answer = channel.transmit(new CommandAPDU(0x00, 0x00, 0x00, 0x00));
		// System.out.println("answer: " + answer.toString());
		// byte r[] = answer.getData();
		// for (int i=0; i<r.length; i++)
		// System.out.print((char)r[i]);
		card.disconnect(false);
	}
}
