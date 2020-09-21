"""
    Author:         Simon Johannesson
    Email:          simonljohannesson@gmail.com, sijohann@kth.se
    Created:        2020-09-18
    Updated:        
    Solves problem: Compares runtimes between different implementations of sorting algorithms.
    Usage:          Assert that all the compiled c files and directories exist as per the calls in the code,
                    and run.
                    The code will likely not run without a lot of work since it was built as a 'one off'
                    script in order to work for this assignment, and so there are a lot of paths hardcoded
                    into the file for faster development.
                    This file is only handed in so that the person grading the lab handin can get a grasp
                    of how I conducted the testing.
    Based on:       None.

"""
import typing
import subprocess
import numpy as np
import matplotlib.pyplot as pyplot

# paths to sorting programs
merge_sort_path = "./out/merge_sort_rec.o"
insertion_sort_path = "./out/insertion_sort.o"
quick_sort_cutoff_path = ".out/quick_sort_cutoff.o"
merge_sort_cutoff_path = "./out/merge_sort_rec_cutoff.o"
quick_sort_path = "./out/quick_sort.o"
quick_sort_mo3_path = "./out/quick_sort_mo3.o"

# paths programs that generate output
rand_output_path = "./out/gen_rand_output.o"
ordered_input_path = "./out/gen_ordered_output.o"
temp_input_path = "./data/temp.txt"



def time_sort(command) -> float:
    output = subprocess.check_output(command, shell=True, stderr=subprocess.STDOUT).decode("utf-8")
    time = output.strip().split("\n")
    if(len(time) == 1):
        return float(time[0])

    else:
        return float(time[2])

def create_command(sort_file_path: str, len_input: int, data_file_path: str) -> str:
    command_list = ["/usr/bin/time -f '%U'"]
    command_list.append(sort_file_path)
    command_list.append(str(len_input))
    command_list.append("<")
    command_list.append(data_file_path)
    command = " ".join(command_list)
    return command

def gen_new_input_file(length: int, biggest_number: int) -> None:
    command = f"./out/gen_rand_output.o {length} {biggest_number} > ./data/temp.txt"
    subprocess.run(command, shell=True)

def gen_new_ordered_input_file(length: int) -> None:
    command = f"./out/gen_ordered_output.o {length} > ./data/temp.txt"
    subprocess.run(command, shell=True)


def compare_algorithms(
            start_data_length: int, 
            biggest_data_length: int, 
            increment: int, 
            xlabel: str,
            ylabel: str,
            title: str,
            filename: str,
            mergesort=False, 
            quicksort=False,
            quicksort_mo3=False,
            mergesort_cutoff=False,
            merge_sort_cutoff_len=15,
            insertionsort=False,
            ordered_input=False):

    mergesort_times = []
    quicksort_times = []
    quicksort_mo3_times = []
    mergesort_cutoff_times = []
    insertionsort_times = []

    test_data = []
    test_data_length = start_data_length

    while (test_data_length <= biggest_data_length):
        # generate random input to file
        if(ordered_input):
            gen_new_ordered_input_file(test_data_length)
        else:
            gen_new_input_file(test_data_length, test_data_length*2)
        
        # store current test data length for plot
        test_data.append(test_data_length)

        if(mergesort):
            # test time for merge sort save best of three runs
            temp_results = []
            for i in range(3):
                command = create_command(merge_sort_path, test_data_length, temp_input_path)
                time = time_sort(command)
                temp_results.append(time)
            mergesort_times.append(min(temp_results))

        if(quicksort):
            # test time for quick sort save best of three runs
            temp_results = []
            for i in range(3):
                command = create_command(quick_sort_path, test_data_length, temp_input_path)
                time = time_sort(command)
                temp_results.append(time)
            quicksort_times.append(min(temp_results))

        if(quicksort_mo3):
            # test time for quick sort mo3 save best of three runs
            temp_results = []
            for i in range(3):
                # command = create_command(quick_sort_mo3_path, test_data_length, temp_input_path)
                command = f"/usr/bin/time -f '%U' ./out/quick_sort_mo3.o {test_data_length} < {temp_input_path}"
                time = time_sort(command)
                temp_results.append(time)
            quicksort_mo3_times.append(min(temp_results))
        
        if(mergesort_cutoff):
            # test time for mergesort cutoff save best of three runs
            temp_results = []
            for i in range(3):
                command = f"/usr/bin/time -f '%U' ./out/merge_sort_rec_cutoff.o {test_data_length} {merge_sort_cutoff_len} < {temp_input_path}"
                time = time_sort(command)
                temp_results.append(time)
            mergesort_cutoff_times.append(min(temp_results))

        if(insertionsort):
            # test time for insertionsort save best of three runs
            temp_results = []
            for i in range(3):
                command = f"/usr/bin/time -f '%U' ./out/insertion_sort.o {test_data_length} < {temp_input_path}"
                time = time_sort(command)
                temp_results.append(time)
            insertionsort_times.append(min(temp_results))

        # increment test data length
        test_data_length += increment

    # graph result
    x = test_data
    
    if(mergesort):
        y1 = mergesort_times
        pyplot.plot(x, y1, color="blue", label="Merge sort", marker="o")
    
    if(quicksort):
        y2 = quicksort_times
        pyplot.plot(x, y2, color="red", label="Quick sort", marker="o")

    if(quicksort_mo3):
        y3 = quicksort_mo3_times
        pyplot.plot(x, y3, color="green", label="Quick sort MO3", marker="o")

    if(mergesort_cutoff):
        y5 = mergesort_cutoff_times
        label = "Mergesort cutoff: " + str(merge_sort_cutoff_len)
        pyplot.plot(x, y5, color="brown", label=label, marker="o")

    if(insertionsort):
        y6 = insertionsort_times
        pyplot.plot(x, y6, color="yellow", label="Insertionsort", marker="o")

    pyplot.xlabel(xlabel)
    pyplot.ylabel(ylabel)
    pyplot.title(title)

    pyplot.legend()
    pyplot.savefig(filename)


def compare_mergesort_cutoff_times(
            start_data_length: int, 
            biggest_data_length: int, 
            increment: int, 
            xlabel: str,
            ylabel: str,
            title: str,
            filename: str,
            insertionsort=False,
            ordered_input=False):

    mergesort_cutoff_times_0 = []
    mergesort_cutoff_times_5 = []
    mergesort_cutoff_times_10 = []
    mergesort_cutoff_times_15 = []
    mergesort_cutoff_times_20 = []
    mergesort_cutoff_times_25 = []
    mergesort_cutoff_times_30 = []

    test_data = []
    test_data_length = start_data_length

    while (test_data_length <= biggest_data_length):
        # generate random input to file
        if(ordered_input):
            gen_new_ordered_input_file(test_data_length)
        else:
            gen_new_input_file(test_data_length, test_data_length*2)
        
        # store current test data length for plot
        test_data.append(test_data_length)


        # test time for mergesort cutoff save best of three runs
        temp_results = []
        merge_sort_cutoff_len = 0
        for i in range(3):
            command = f"/usr/bin/time -f '%U' ./out/merge_sort_rec_cutoff.o {test_data_length} {merge_sort_cutoff_len} < {temp_input_path}"
            time = time_sort(command)
            temp_results.append(time)
        mergesort_cutoff_times_0.append(min(temp_results))

        # test time for mergesort cutoff save best of three runs
        temp_results = []
        merge_sort_cutoff_len = 5
        for i in range(3):
            command = f"/usr/bin/time -f '%U' ./out/merge_sort_rec_cutoff.o {test_data_length} {merge_sort_cutoff_len} < {temp_input_path}"
            time = time_sort(command)
            temp_results.append(time)
        mergesort_cutoff_times_5.append(min(temp_results))


        # test time for mergesort cutoff save best of three runs
        temp_results = []
        merge_sort_cutoff_len = 10
        for i in range(3):
            command = f"/usr/bin/time -f '%U' ./out/merge_sort_rec_cutoff.o {test_data_length} {merge_sort_cutoff_len} < {temp_input_path}"
            time = time_sort(command)
            temp_results.append(time)
        mergesort_cutoff_times_10.append(min(temp_results))


        # test time for mergesort cutoff save best of three runs
        temp_results = []
        merge_sort_cutoff_len = 15
        for i in range(3):
            command = f"/usr/bin/time -f '%U' ./out/merge_sort_rec_cutoff.o {test_data_length} {merge_sort_cutoff_len} < {temp_input_path}"
            time = time_sort(command)
            temp_results.append(time)
        mergesort_cutoff_times_15.append(min(temp_results))


        # test time for mergesort cutoff save best of three runs
        temp_results = []
        merge_sort_cutoff_len = 20
        for i in range(3):
            command = f"/usr/bin/time -f '%U' ./out/merge_sort_rec_cutoff.o {test_data_length} {merge_sort_cutoff_len} < {temp_input_path}"
            time = time_sort(command)
            temp_results.append(time)
        mergesort_cutoff_times_20.append(min(temp_results))


        # test time for mergesort cutoff save best of three runs
        temp_results = []
        merge_sort_cutoff_len = 25
        for i in range(3):
            command = f"/usr/bin/time -f '%U' ./out/merge_sort_rec_cutoff.o {test_data_length} {merge_sort_cutoff_len} < {temp_input_path}"
            time = time_sort(command)
            temp_results.append(time)
        mergesort_cutoff_times_25.append(min(temp_results))

        # test time for mergesort cutoff save best of three runs
        temp_results = []
        merge_sort_cutoff_len = 30
        for i in range(3):
            command = f"/usr/bin/time -f '%U' ./out/merge_sort_rec_cutoff.o {test_data_length} {merge_sort_cutoff_len} < {temp_input_path}"
            time = time_sort(command)
            temp_results.append(time)
        mergesort_cutoff_times_30.append(min(temp_results))

        # increment test data length
        test_data_length += increment


    # graph result
    x = test_data
    
    y0 = mergesort_cutoff_times_0
    label = "Mergesort cutoff: " + str(0)
    pyplot.plot(x, y0, color="green", label=label, marker="o")

    y5 = mergesort_cutoff_times_5
    label = "Mergesort cutoff: " + str(5)
    pyplot.plot(x, y5, color="blue", label=label, marker="o")

    y10 = mergesort_cutoff_times_10
    label = "Mergesort cutoff: " + str(10)
    pyplot.plot(x, y10, color="red", label=label, marker="o")

    y15 = mergesort_cutoff_times_15
    label = "Mergesort cutoff: " + str(15)
    pyplot.plot(x, y15, color="orange", label=label, marker="o")

    y20 = mergesort_cutoff_times_20
    label = "Mergesort cutoff: " + str(20)
    pyplot.plot(x, y20, color="pink", label=label, marker="o")

    y25 = mergesort_cutoff_times_25
    label = "Mergesort cutoff: " + str(25)
    pyplot.plot(x, y25, color="yellow", label=label, marker="o")

    y30 = mergesort_cutoff_times_30
    label = "Mergesort cutoff: " + str(30)
    pyplot.plot(x, y30, color="brown", label=label, marker="o")

    pyplot.xlabel(xlabel)
    pyplot.ylabel(ylabel)
    pyplot.title(title)

    pyplot.legend()
    pyplot.savefig(filename)


if __name__ == "__main__":
    if(0):
        compare_algorithms(
            0,
            100000, 
            10000, 
            "Data length", 
            "Time", 
            "Time to sort randomly ordered lists", 
            "test.png",
            mergesort=True,
            insertionsort=True, 
            quicksort=False, 
            quicksort_mo3=False,
            ordered_input=False
        )
    if(1):
        compare_mergesort_cutoff_times(
            40000000,
            50000000,
            10000000,
            "Data length",
            "Time",
            "Time to sort randomly ordered lists",
            "test.png",
            ordered_input=False
        )
    pass