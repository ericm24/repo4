/**
 *  
 * @author eric
 * 
 */
package com.eric.shell.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class PathController
{

	static String[] mapToStringArray(Map<String, String> map)
	{
		final String[] strings = new String[map.size()];
		int i = 0;
		for (Map.Entry<String, String> e : map.entrySet())
		{
			strings[i] = e.getKey() + '=' + e.getValue();
			i++;
		}

		return (strings);
	}

	public static void main(String[] arguments) throws Exception
	{
		final Map<String, String> env = new HashMap<String, String>(
				System.getenv());

		env.put("Path", "src/main/resources/;" + env.get("Path"));

		final String[] strings = mapToStringArray(env);

		String line = null;
		Process proc = null;

		// This Works
		String basePath = "src/main/resources/";

		String command = null; // "tree6.com /A";

		// Runtime.getRuntime().exec("cmd /C start foo.bat", strings);

		// int i = 8;
		// System.out.println("Attempted To RESET Path Set To: " + strings[ i
		// ]);
		// System.out.println("Current Path Is: " + env.get("Path"));

		try
		{

			// System.out.println( strings );
			// Change Path and Run...fails.
			// proc = Runtime.getRuntime().exec("tree3.com /A", strings);

			// Run from established os-path...works!
			// proc = Runtime.getRuntime().exec("tree.com /A");

			// FQDN....works!
			// proc = Runtime.getRuntime().exec("c:\\dev\\cmdz2\\tree3.com /A");

			command = (basePath != null) ? basePath + command : command;

			System.out.println("Attempting To Run: " + command);

			// Run from embedded in project...
			proc = Runtime.getRuntime().exec(command);

			BufferedReader input = new BufferedReader(new InputStreamReader(
					proc.getInputStream()));

			System.out.println("Output Starts...");

			while ((line = input.readLine()) != null)
			{
				System.out.println(line);
			}

			input.close();

			System.out.println("Output Ends...");

		}
		catch (Exception ex)
		{
			System.out.println("Exception Encountered:  " + ex.getMessage());
		}

		return;
	}

}
