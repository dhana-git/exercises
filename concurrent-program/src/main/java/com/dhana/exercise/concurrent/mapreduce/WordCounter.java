package com.dhana.exercise.concurrent.mapreduce;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class WordCounter {

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		String dir = "C:/FDC/Workspaces/workspace_mine/ConcProgm/../..";
		String searchWord = "concurrent";
		Folder folder = new Folder(new File(dir));
		ForkJoinPool forkJoinPool = new ForkJoinPool();
		FolderSearchTask rootTask = new FolderSearchTask(folder, searchWord);
		RecursiveTask<SearchResult> result = (RecursiveTask<SearchResult>) forkJoinPool.submit(rootTask);
		try {
			SearchResult searchResult = result.get();
			System.out.println("Count of given word:" + searchResult.getCount());
			System.out.println("Number of files searched:" + searchResult.getFileNames().size());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		long timeTaken = System.currentTimeMillis() - startTime;
		System.out.println("TAT:" + timeTaken + " ms");
	}

	public static String[] wordsInLine(String line) {
		return line.split("(\\s|\\p{Punct}|\\p{Space})+");

	}

	public static SearchResult countOccurrences(Document document, String searchWord) {
		long count = 0L;
		SearchResult searchResult = new SearchResult();
		searchResult.addFileName(document.getName());
		System.out.println("Searching at :" + document.getName());
		for (String line : document.getLines()) {
			for (String word : wordsInLine(line)) {
				if (searchWord.equals(word)) {
					count++;
				}
			}
		}

		searchResult.setCount(count);
		return searchResult;
	}
}

class SearchResult {
	private Long count;
	private List<String> fileNames;

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public List<String> getFileNames() {
		return fileNames;
	}

	public void setFileNames(List<String> fileNames) {
		this.fileNames = fileNames;
	}

	public void addFileName(String fileName) {
		if (this.fileNames == null) {
			this.fileNames = new ArrayList<>();
		}
		this.fileNames.add(fileName);
	}

	public int getNumberOfFilesEncountered() {
		return this.fileNames.size();
	}
}

/**
 * Represents a document.
 */
class Document {
	private List<String> lines;
	private String name;

	public String getName() {
		return name;
	}

	public List<String> getLines() {
		return lines;
	}

	public Document(File file) {
		List<String> lines = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				lines.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.name = file.getAbsolutePath();
		this.lines = lines;
	}
}

/**
 * Represents a folder.
 *
 */
class Folder {
	private List<Document> documents;
	private List<Folder> subFolders;

	public List<Document> getDocuments() {
		return documents;
	}

	public List<Folder> getSubFolders() {
		return subFolders;
	}

	public Folder(File dir) {
		List<Document> documents = new ArrayList<>();
		List<Folder> subFolders = new ArrayList<>();
		for (File file : dir.listFiles()) {
			if (file.isDirectory()) {
				subFolders.add(new Folder(file));
			} else if (isSearchable(file)) {
				documents.add(new Document(file));
			}
		}
		this.documents = documents;
		this.subFolders = subFolders;
	}

	private Boolean isSearchable(File file) {
		// FIXME : Check for character encoding instead of file extension.
		if (file.isFile() && file.canRead() && (file.getName().matches("^.*\\.(txt|java)+"))) {
			return true;
		}
		return false;
	}
}

/**
 * Result-bearing task returning the count of words in a document.
 * 
 */
class DocumentSearchTask extends RecursiveTask<SearchResult> {
	private static final long serialVersionUID = 1L;
	private Document document;
	private String searchWord;

	public DocumentSearchTask(Document document, String searchWord) {
		this.document = document;
		this.searchWord = searchWord;
	}

	@Override
	protected SearchResult compute() {
		return WordCounter.countOccurrences(document, searchWord);
	}
}

/**
 * Result-bearing task returning the count of word in a folder/sub folder.
 */
class FolderSearchTask extends RecursiveTask<SearchResult> {
	private static final long serialVersionUID = 1L;
	private Folder folder;
	private String searchWord;

	public FolderSearchTask(Folder folder, String searchWord) {
		this.folder = folder;
		this.searchWord = searchWord;

	}

	@Override
	protected SearchResult compute() {
		long count = 0L;
		List<String> fileNames = new ArrayList<>();
		SearchResult searchResult = new SearchResult();

		List<RecursiveTask<SearchResult>> chunks = new ArrayList<>();
		for (Folder folder : folder.getSubFolders()) {
			FolderSearchTask task = new FolderSearchTask(folder, searchWord);
			chunks.add(task);
			task.fork(); // task.invoke();
		}

		for (Document document : folder.getDocuments()) {
			DocumentSearchTask task = new DocumentSearchTask(document, searchWord);
			chunks.add(task);
			task.fork(); // task.fork();
		}

		for (RecursiveTask<SearchResult> chunk : chunks) {
			SearchResult chunkSearchResult = chunk.join();
			count = count + chunkSearchResult.getCount();
			fileNames.addAll(chunkSearchResult.getFileNames());
		}

		searchResult.setCount(count);
		searchResult.setFileNames(fileNames);
		return searchResult;
	}

}