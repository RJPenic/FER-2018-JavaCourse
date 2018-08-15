package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.java.custom.collections.Dictionary;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.ColorCommand;
import hr.fer.zemris.lsystems.impl.commands.DrawCommand;
import hr.fer.zemris.lsystems.impl.commands.PopCommand;
import hr.fer.zemris.lsystems.impl.commands.PushCommand;
import hr.fer.zemris.lsystems.impl.commands.RotateCommand;
import hr.fer.zemris.lsystems.impl.commands.ScaleCommand;
import hr.fer.zemris.lsystems.impl.commands.SkipCommand;
import hr.fer.zemris.math.Vector2D;

/**
 * Class that represents implementation of LSystemBuilder interface
 * 
 * @author Rafael Josip Penić
 * @version 1.0
 *
 */
public class LSystemBuilderImpl implements LSystemBuilder {

	public static final double DEFAULT_UNIT_LENGTH = 0.1;
	public static final double DEFAULT_UNIT_LENGTH_DEGREE_SCALER = 1;
	public static final double DEFAULT_ORIGIN_X = 0;
	public static final double DEFAULT_ORIGIN_Y = 0;
	public static final double DEFAULT_ANGLE = 0;
	public static final String DEFAULT_AXIOM = "";
	
	/**
	 * Dictionary that stores registered productions
	 */
	private Dictionary registeredProductions;

	/**
	 * Dictionary that stores registered commands
	 */
	private Dictionary registeredCommands;

	/**
	 * value that represents length of one step of the turtle
	 */
	private double unitLength;

	/**
	 * scaling factor for unitLength variable(depening on the level of fractal)
	 */
	private double unitLengthDegreeScaler;

	/**
	 * Starting point of the turtle
	 */
	private Vector2D origin;

	/**
	 * Represents turtle orientation(e.g. if the angle is 0 that means that turtle
	 * is looking in the right way)
	 */
	private double angle;

	/**
	 * Starting string from which the development of the fractal starts
	 */
	private String axiom;

	/**
	 * Helper class that will be returned in when build() method is called. It
	 * implements LSystem interface.
	 * 
	 * @author Rafael Josip Penić
	 * @version 1.0
	 *
	 */
	private class LSystemImpl implements LSystem {

		/**
		 * Method that draws given level of certain fractal in GUI interface
		 * 
		 * @param arg0
		 *            level of development
		 * @param arg1
		 *            painter object that is used for drawing
		 */
		@Override
		public void draw(int arg0, Painter arg1) {
			Context ctx = new Context();
			Vector2D tempVector = new Vector2D(1, 0);
			TurtleState state = new TurtleState(origin.copy(), tempVector.rotated(angle), Color.BLACK,
					unitLength * Math.pow(unitLengthDegreeScaler, arg0));
			ctx.pushState(state);

			String tempString = generate(arg0);

			for (int i = 0; i < tempString.length(); i++) {
				char c = tempString.charAt(i);
				Command comm = (Command) registeredCommands.get(c);

				if (comm != null) {
					comm.execute(ctx, arg1);
				}
			}
		}

		/**
		 * Method that generates string for a certain level(that is given as argument)
		 * using the axiom as a "starting point"
		 * 
		 * @param arg0
		 *            level of development
		 * @return newly generated string
		 */
		@Override
		public String generate(int arg0) {
			String tempString = axiom;

			for (int i = 0; i < arg0; i++) {
				StringBuilder sb = new StringBuilder();

				for (int j = 0; j < tempString.length(); j++) {
					char c = tempString.charAt(j);
					Object replacement = registeredProductions.get(c);
					if (replacement != null) {
						sb.append(replacement.toString());
					} else {
						sb.append(c);
					}
				}

				tempString = sb.toString();
			}

			return tempString;
		}
	}

	/**
	 * Constructor for LSystemBuilderImpl. Allocates dictionaries and initializes
	 * variables on default values.
	 */
	public LSystemBuilderImpl() {
		registeredProductions = new Dictionary();
		registeredCommands = new Dictionary();

		unitLength = DEFAULT_UNIT_LENGTH;
		unitLengthDegreeScaler = DEFAULT_UNIT_LENGTH_DEGREE_SCALER;
		origin = new Vector2D(DEFAULT_ORIGIN_X, DEFAULT_ORIGIN_Y);
		angle = DEFAULT_ANGLE;
		axiom = DEFAULT_AXIOM;
	}

	/**
	 * Method that builds new LSystemImpl and returns it.
	 * 
	 * @return new LSystemImpl object
	 */
	@Override
	public LSystem build() {
		return new LSystemImpl();
	}

	/**
	 * Constructs LSystemBuilder object from the given string array.
	 * 
	 * @param arg0
	 *            String array(text)
	 * @return LSystemBuilder constructed from the given text
	 * @throws NullPointerException
	 *             if the given string array is null
	 * @throws IllegalArgumentException
	 *             if the given text is not valid
	 */
	@Override
	public LSystemBuilder configureFromText(String[] arg0) {
		if (arg0 == null)
			throw new NullPointerException("Cannot configure from the string array that is null.");

		for (int i = 0; i < arg0.length; i++) {
			String[] tempStringArray = arg0[i].trim().split("\\s+");
			
			if (tempStringArray.length != 0 && !(tempStringArray[0].isEmpty())) {	// In case of an empty line(or line that only consists of spaces and tabs)
																					// there is no need to "decipher" what is in it.
				try {
					switch (tempStringArray[0]) {
					case ("origin"):
						origin = new Vector2D(Double.parseDouble(tempStringArray[1]),	// Notice that if more than two arguments are given, only first 2 will be "taken"
								Double.parseDouble(tempStringArray[2]));				// and rest will be ignored(this makes program more robust). Similar thing will happen with
						break;															// most other directives and commands too.

					case ("angle"):
						angle = Double.parseDouble(tempStringArray[1]);
						break;

					case ("unitLength"):
						unitLength = Double.parseDouble(tempStringArray[1]);
						break;

					case ("unitLengthDegreeScaler"):
						if (tempStringArray.length > 3) {
							if (!tempStringArray[2].equals("/"))		// If more than 2 arguments are given(+ name of the directive), that means that degree scaler
								throw new IllegalArgumentException(		// is given using a slash("/") which means that the second argument must be "/".
										"If more than 1 argument is present, '/' is needed.");
							unitLengthDegreeScaler = Double.parseDouble(tempStringArray[1])
									/ Double.parseDouble(tempStringArray[3]);
							
						} else if (tempStringArray.length == 3) {								// This means that 2 arguments are given which means one of them must include "/".
							if (tempStringArray[1].contains("/")) {								// This line covers the case when the slash is connected with the first argument
								tempStringArray[1] = tempStringArray[1].replaceFirst("/", "");
								unitLengthDegreeScaler = Double.parseDouble(tempStringArray[1])
										/ Double.parseDouble(tempStringArray[2]);
							} else {
								tempStringArray[2] = tempStringArray[2].replaceFirst("/", "");	// This line covers the case when the slash is connected with the second argument.
								unitLengthDegreeScaler = Double.parseDouble(tempStringArray[1])	// Notice that if more than one slash is entered, NumberFormatException will be thrown
										/ Double.parseDouble(tempStringArray[2]);
							}
						} else {										// If there aren't more than 2 arguments nor 2 arguments that means we have one(or none) argument 
							if (tempStringArray[1].contains("/")) {
								String[] array = tempStringArray[1].split("/");
								unitLengthDegreeScaler = Double.parseDouble(array[0]) / Double.parseDouble(array[1]);
							} else {
								unitLengthDegreeScaler = Double.parseDouble(tempStringArray[1]);
							}
						}
						break;

					case ("command"):
						if (tempStringArray[1].length() != 1) {		// if the first arguments length isn't one that means that it cannot be converted to character
							throw new IllegalArgumentException(		// and exception is thrown.
									"When using 'command' directive character must be the first argument.");
						}

						if (!(tempStringArray[2].equals("push") || tempStringArray[2].equals("pop"))) { // push and pop command must be processed separately from other commands 
							registerCommand(tempStringArray[1].charAt(0),								// because they don't need arguments(others take their argument from tempStringArray[3])
									tempStringArray[2] + " " + tempStringArray[3]);
						} else {
							registerCommand(tempStringArray[1].charAt(0), tempStringArray[2]);			// this covers push and pop case
						}
						break;

					case ("axiom"):
						axiom = tempStringArray[1];
						break;

					case ("production"):
						if (tempStringArray[1].length() != 1) {
							throw new IllegalArgumentException(
									"When using 'production' directive first argument must be character.");
						}
						registerProduction(tempStringArray[1].charAt(0), tempStringArray[2]);
						break;

					default:									// in case of unrecognizable directive
						throw new IllegalArgumentException(
								"'" + tempStringArray[0] + "' is not a recognizable directive.");
					}

				} catch (IndexOutOfBoundsException ex) {	// in case a directive doesn't receive enough arguments
					throw new IllegalArgumentException(
							"'" + tempStringArray[0] + "' directive must have appropriate arguments.");
				} catch (NumberFormatException ex) {		// in case certain numbers cannot be parsed
					throw new IllegalArgumentException(
							"'" + tempStringArray[0] + "' directive doesn't have valid arguments.");
				} catch (IllegalArgumentException ex) {		// in case one of registerCommand commands throws IllegalArgumentException
					throw ex;
				}
			}
		}

		return this;
	}

	/**
	 * Adds an entry into the registered commands dictionary where the given
	 * character is a key and given string is converted to a command(if possible)
	 * and used as entry value.
	 * 
	 * @param arg0
	 *            character that will be used as entry key
	 * @param arg1
	 *            string of command that will be used as entry value
	 * 
	 * @return reference on this object
	 * @throws NullPointerException
	 *             if the given string is null
	 * @throws IllegalArgumentException
	 *             if the given string is not a valid command
	 */
	@Override
	public LSystemBuilder registerCommand(char arg0, String arg1) {
		if (arg1 == null)
			throw new NullPointerException("Cannot read commands from the null string.");
		Command comm;
		String[] tempStringArray = arg1.trim().split("\\s+");

		try {
			switch (tempStringArray[0].toLowerCase()) {
			case ("push"):					// Notice that even if we give arguments to push command
				comm = new PushCommand();	// method will ignore them.
				break;

			case ("pop"):
				comm = new PopCommand();
				break;

			case ("draw"):
				comm = new DrawCommand(Double.parseDouble(tempStringArray[1]));
				break;

			case ("skip"):
				comm = new SkipCommand(Double.parseDouble(tempStringArray[1]));
				break;

			case ("rotate"):
				comm = new RotateCommand(Double.parseDouble(tempStringArray[1]));
				break;

			case ("scale"):
				comm = new ScaleCommand(Double.parseDouble(tempStringArray[1]));
				break;

			case ("color"):
				comm = new ColorCommand(Color.decode("0x" + tempStringArray[1]));
				break;

			default:
				throw new IllegalArgumentException("'" + tempStringArray[0] + "' is not a recognizable command.");
			}
		} catch (IndexOutOfBoundsException ex) {	// if the IndexOutOfBoundsException is encountered that means that needed argument for some command wasn't provided
			throw new IllegalArgumentException("'" + tempStringArray[0] + "' must have an appropriate argument.");
		} catch (NumberFormatException ex) {		// thrown in case something cannot be parsed correctly
			throw new IllegalArgumentException("'" + tempStringArray[0] + "' argument is not valid.");
		}

		registeredCommands.put(arg0, comm);
		return this;
	}

	/**
	 * Registers given production in the production dictionary.
	 * 
	 * @param arg0
	 *            character that will be used as entry key
	 * @param arg1
	 *            string that will be used as entry value
	 * 
	 * @return reference on this object
	 */
	@Override
	public LSystemBuilder registerProduction(char arg0, String arg1) {
		registeredProductions.put(arg0, arg1);
		return this;
	}

	/**
	 * Sets angle on the given value
	 * 
	 * @param arg0
	 *            new value of angle
	 * 
	 * @return reference on this object
	 */
	@Override
	public LSystemBuilder setAngle(double arg0) {
		angle = arg0;
		return this;
	}

	/**
	 * Sets axiom on the given string
	 * 
	 * @param arg0
	 *            new axiom
	 * 
	 * @return reference on this object
	 */
	@Override
	public LSystemBuilder setAxiom(String arg0) {
		if (arg0 == null)
			throw new NullPointerException("Axiom cannot be null.");

		axiom = arg0;
		return this;
	}

	/**
	 * Sets given vector as new origin
	 * 
	 * @param arg0
	 *            x-component of the new origin
	 * @param arg1
	 *            y-component of the new origin
	 * @return reference on this object
	 */
	@Override
	public LSystemBuilder setOrigin(double arg0, double arg1) {
		origin = new Vector2D(arg0, arg1);
		return this;
	}

	/**
	 * Sets unit length on the given value
	 * 
	 * @param arg0
	 *            new value of unit length
	 * 
	 * @return reference on this object
	 */
	@Override
	public LSystemBuilder setUnitLength(double arg0) {
		unitLength = arg0;
		return this;
	}

	/**
	 * Sets unit length degree scaler on the given value
	 * 
	 * @param arg0
	 *            new value of degree scaler
	 * 
	 * @return reference on this object
	 */
	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double arg0) {
		unitLengthDegreeScaler = arg0;
		return this;
	}

}
