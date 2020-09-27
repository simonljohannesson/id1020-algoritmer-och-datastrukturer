import subprocess
import matplotlib.pyplot as pyplot
import os
import typing
import re


def compare_binary_search_st_and_bst(
                                    min_word_len: int, 
                                    n_size: int, 
                                    BST=False, 
                                    BinarySearchST=False
                                    ):
    # set working dir
    wdir = "/home/simon/dev/school-projects/id1020-algoritmer-och-datastrukturer/lab-3/java-impl/bin"
    os.chdir(wdir)

    result_list = []

    if(BST):
        # BST
        data_file_path = "/home/simon/dev/school-projects/id1020-algoritmer-och-datastrukturer/lab-3/java-impl/the-text.txt"
        program_file_path = "FrequencyCounterBST"
        # min_word_len = 1
        # n_size = 10
        command = f"java {program_file_path} {min_word_len} {n_size} {data_file_path}"
        bst_output = subprocess.check_output(command, shell=True).decode("utf-8")
        (bst_n_list, bst_times_list) = parse_output(bst_output)
        if (len(result_list) == 0):
            result_list.append(bst_n_list)
        result_list.append(bst_times_list)
        
            

    if(BinarySearchST):
        # BinarySearchST
        data_file_path = "/home/simon/dev/school-projects/id1020-algoritmer-och-datastrukturer/lab-3/java-impl/the-text.txt"
        program_file_path = "FrequencyCounterBinarySearchST"
        # min_word_len = 1
        # n_size = 10
        command = f"java {program_file_path} {min_word_len} {n_size} {data_file_path}"
        bindary_search_st_output = subprocess.check_output(command, shell=True).decode("utf-8")
        (bindary_search_st_n_list, bindary_search_st_times_list) = parse_output(bindary_search_st_output)
        if (len(result_list) == 0):
            result_list.append(bindary_search_st_n_list)
        result_list.append(bindary_search_st_times_list)

    if(BST and BinarySearchST):
        assert len(bindary_search_st_n_list) == len(bst_n_list)
    
    return result_list

def plot_graph( n_list, 
                bst_times_list, 
                bindary_search_st_times_list, 
                title, 
                xlabel, 
                ylabel, 
                filename):
    # graph result
    x = n_list

    y1 = bindary_search_st_times_list
    pyplot.plot(x, y1, color="blue", label="BinarySearchST", marker="o")

    y2 = bst_times_list
    pyplot.plot(x, y2, color="red", label="BST", marker="o")

    pyplot.xlabel(xlabel)
    pyplot.ylabel(ylabel)
    pyplot.title(title)

    pyplot.legend()
    pyplot.savefig(filename)

def parse_output(output: str) -> typing.List:
    find_n_pattern = re.compile("(?<=n=)\\d+(?= time=)")
    n_list = re.findall(find_n_pattern, output)
    for i in range(len(n_list)):
        n_list[i] = int(n_list[i])
    find_time_pattern = re.compile("(?<=time=)\\d+\.\\d+E?\\d?")
    times_list = re.findall(find_time_pattern, output)
    for i in range(len(times_list)):
        times_list[i] = float(times_list[i])

    return (n_list, times_list)

if __name__ == "__main__":
    results = compare_binary_search_st_and_bst(1, 1400, BST=True, BinarySearchST=True)
    print(results[0])
    print(results[1])
    print(results[2])
    plot_graph(
        results[0], 
        results[1], results[2], 
        "Comparison in runtimes", 
        "Words read in hundreds", 
        "Time consumption (nanoseconds)",
        "test4.png"
        )
