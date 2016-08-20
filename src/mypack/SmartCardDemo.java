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

		ResponseAPDU answer = channel.transmit(new CommandAPDU(CMD_SELECT_MF));
		System.out.println("answer: " + answer.toString());

		answer = channel.transmit(new CommandAPDU(CMD_SELECT_DF1));
		System.out.println("answer: " + answer.toString());

		answer = channel.transmit(new CommandAPDU(CMD_SELECT_PERSONAL_FILE));
		System.out.println("answer: " + answer.toString());

		answer = channel.transmit(new CommandAPDU(CMD_READ_BINARY));
		System.out.println("answer: " + new String(answer.getData()));
	
		card.disconnect(false);
	}
}
