package com.mycompany.interviews;

import java.util.*;

// TODO: Implement this class
public class AnagramGame implements IAnagramGame
{
	String baseWord;
	IWordDictionary dictionary;
	public List<String> scoreList = new ArrayList<>();

	public AnagramGame(String baseWord, IWordDictionary dictionary)
	{
		this.baseWord = baseWord;
		this.dictionary = dictionary;
	}

	public void submitWord(String word)
	{

	}


	public int evaluateWord(String word)
	{
		
 	}


	public int getScoreAtPosition(int position)
	{
		if (position < scoreList.size()) {
			return scoreList.get(position).length();
		}
		return -1;
	}


	public String getWordAtPosition(int position)
	{
		if (position < scoreList.size()) {
			return scoreList.get(position);
		}
		return null;
	}
}
