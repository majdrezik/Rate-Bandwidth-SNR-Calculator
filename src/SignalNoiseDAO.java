
import java.util.Scanner;

public class SignalNoiseDAO {
	int numOfQuestions = 4;
	int numOfParams = 3;
	double rate, bandwidth;
	double SNdb;
	double snP;

//----------------------------------------------------------------------------
	public void askParams() {
		// int rate, bandwidth, SN;
		String[] questions = new String[numOfQuestions];
		questions[0] = "Please enter your Signal to Noise Ratio (in db): ";
		questions[1] = "Please enter your Bandwidth (in Hz): ";
		questions[2] = "Please enter your Rate (in bit/sec): ";
		questions[3] = "Please choose what you want to calculate: ";
		String dashes = "---------------------------------------------";
		String printRate = "Rate.";
		String printSNRatio = "Signal to Noise Ratio.";
		String printBandwidth = "Bandwidth.";
		String[] params = new String[numOfParams];
		params[0] = printRate;
		params[1] = printBandwidth;
		params[2] = printSNRatio;

		print("");
		print(questions[3]);
		for (int i = 0; i < numOfParams; i++) {
			printWithoutNewLine(i + 1 + ". " + params[i]);
		}
		print("");
		print("Choose your answer <number>: ");
		Scanner scan = new Scanner(System.in);

		int answer = 0;
		answer = scan.nextInt();
		while (illegalInput(answer)) {
			answer = scan.nextInt();
		}

		switch (answer) {
			case 1:
				print(questions[1]);
				bandwidth = scan.nextInt();
				print(questions[0]);
				SNdb = scan.nextInt();
				snP = transformSNR(SNdb);
				print("SN in volt is: " + snP + "[Volt]");
				print("your bandwidth is: " + bandwidth);
				rate = calculateRate(bandwidth, snP);
				printWithoutNewLine(dashes);
				print("\n	your rate is: " + (int) rate + "[bps]");
				printWithoutNewLine(dashes);
	
				break;
			case 2:
				print(questions[2]);
				rate = scan.nextInt();
				print(questions[0]);
				SNdb = scan.nextInt();
				snP = transformSNR(SNdb);
				print("SN in volt is: " + snP);
				bandwidth = calculateBandwidth(rate, snP);
				printWithoutNewLine(dashes);
				print("\n	your bandwidth is: " + (int) bandwidth + "[Hz]");
				printWithoutNewLine(dashes);
				break;
			case 3:
				print(questions[2]);
				rate = scan.nextInt();
				print(questions[1]);
				bandwidth = scan.nextInt();
				snP = calculateSNRatio(rate, bandwidth);
				printWithoutNewLine(dashes);
				print("\n	your Signal to Noise ratio is: " + (int) snP + " [Volt]");
				printWithoutNewLine(dashes);
				break;
			default:
				print("Invalid input!");
		}

		scan.close(); // close the scanner after use.

	} // END askParams()

//----------------------------------------------------------------------------	

	// Transform Signal to Noise Ratio from [dB] to [volt].
	private double transformSNR(double sNdb) {
		return Math.pow(10, sNdb / 10);
	}

//----------------------------------------------------------------------------	
	// calculate SN with power ratio and not db. (transform)
	private double calculateSNRatio(double rate, double bandwidth) {

		return Math.pow(2, rate / bandwidth) - 1;
	}

//----------------------------------------------------------------------------
	private double calculateBandwidth(double rate, double snP) {
		return (rate / calculateLogBase2(1 + snP));
	}

	private double calculateLogBase2(double d) {
		return (Math.log(d) / Math.log(2));
	}

//----------------------------------------------------------------------------
	private double calculateRate(double bandwidth, double sNdb) {
		return (bandwidth * calculateLogBase2(1 + sNdb));
	}

//----------------------------------------------------------------------------
	public void print(String str) {
		System.out.println(str + "\n");
	}

	public void printWithoutNewLine(String str) {
		System.out.println(str);
	}

//----------------------------------------------------------------------------
	public boolean illegalInput(int num) {
		return (num >= 0 && num <= numOfParams) ? false : true;
	}

	public static void main(String[] args) {
		SignalNoiseDAO dao = new SignalNoiseDAO();
		dao.askParams();
	}
}
