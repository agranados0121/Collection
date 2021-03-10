#include <iostream>
#include <stdio.h>
#include<unistd.h>
#include <stdlib.h>
#include <csignal>
#include <map>
#include<sys/stat.h>
#include <fcntl.h>
#include <errno.h>  
#include<cstring>
#include<string>
using namespace std;

const int TEXT_LEN = 4096;
#define COUNT_FILEPATH "out.txt"
#define DICT = "en_US"
#define PATH = "/usr/bin/hunspell"

int launchHunspell(int* toHunspell, int* toURLVisitor, char path[], char dict[]) {
	int childId = fork();
	if (childId < 0) {
		fprintf(stderr, "FORK() failed", strerror(errno));
		exit(EXIT_FAILURE);
	}
	if (childId == 0) {
		close(toHunspell[1]);
		dup2(toHunspell[0], STDIN_FILENO);

		close(toURLVisitor[0]);
		dup2(toURLVisitor[1], STDOUT_FILENO);
		execl(path, "hunspell", "-a", "-d", dict, NULL);
		fprintf(stderr, "Cannot launch hunspell");
		exit(EXIT_FAILURE);
	}
	close(toHunspell[0]);
	close(toURLVisitor[1]);

	return childId;
}

int launchHunspell(int* toHunspell, int* toURLVisitor){
  int childId = fork();
  if (childId < 0) {
    fprintf(stderr, "FORK() failed", strerror(errno));
    exit(EXIT_FAILURE);
  }
  if (childId == 0) {
    close(toHunspell[1]);
    dup2(toHunspell[0], STDIN_FILENO);

    close(toURLVisitor[0]);
    dup2(toURLVisitor[1], STDOUT_FILENO);
    execl("/usr/bin/hunspell", "hunspell", "-a", "-d", "en_US", NULL);
    fprintf(stderr, "Cannot launch hunspell");
    exit(EXIT_FAILURE);
  }
  close(toHunspell[0]);
  close(toURLVisitor[1]);

  return childId;

}


int launchURLVisitor(int* toHunspell, int* toURLVisitor, char*  depth, char  url[]) {
	int childId = fork();
	if (childId < 0) {
		fprintf(stderr, "FORK() failed", strerror(errno));
		exit(EXIT_FAILURE);
	}
	if (childId == 0) {
		dup2(toHunspell[1], STDOUT_FILENO);

		dup2(toURLVisitor[0], STDIN_FILENO);
		execl("/usr/local/bin/java", "java", "Crawler", url, depth, NULL);
		fprintf(stderr, "Cannot launch Java");
		exit(EXIT_FAILURE);
	}

	close(toHunspell[1]);
	close(toURLVisitor[0]);

	return childId;
}


int main(int argc, char* argv[]) {
	int numBytes;
	int toHunspell[2];
	int toURLVisitor[2];
	char text[TEXT_LEN];
	int hunspellId;
	map<string, int> count;
	if (pipe(toHunspell) < 0 || pipe(toURLVisitor) < 0) {
		fprintf(stderr, "pipe() failed", strerror(errno));
		exit(EXIT_FAILURE);
	}
	if(argc == 5){
	   hunspellId = launchHunspell(toHunspell, toURLVisitor,argv[3],argv[4]);
	}
	else{
	   hunspellId = launchHunspell(toHunspell, toURLVisitor);
	}
	int urlId = launchURLVisitor(toHunspell, toURLVisitor, argv[2], argv[1]);
	wait();
	kill(hunspellId, SIGINT);
	wait();

	int fileD = open(COUNT_FILEPATH, O_RDONLY);
	if (fileD < 0) {
		fprintf(stderr, "No file");
		exit(EXIT_FAILURE);
	}


	while ((numBytes = read(fileD, text, TEXT_LEN - 1))) {
		text[numBytes] = '\0';
		printf("%s", text);
	}
	close(fileD);
	return(EXIT_SUCCESS);
}
