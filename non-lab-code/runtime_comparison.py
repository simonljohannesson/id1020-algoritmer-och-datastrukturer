import typing
import subprocess
import numpy as np
import matplotlib.pyplot as pyplot


merge_sort_path = "./out/merge_sort_rec.o"
insertion_sort_path = "./out/insertion_sort.o"
rand_output_path = "./out/gen_rand_output.o"
temp_input_path = "./data/temp.txt"
merge_sort_cutoff_path = "./out/merge_sort_rec_cutoff.o"
quick_sort_path = "./out/quick_sort.o"
quick_sort_mo3_path = "./out/quick_sort_mo3.o"
ordered_input_path = "./out/gen_ordered_output.o"

data_available_merge_insertion = [10, 100, 1000, 10000, 100000]


# def compare_insertion_and_merge_sort(command: typing.List[str, str, str]):


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

def create_data_file_path(num: int) -> str:
    return "./data/" + str(num) + ".txt"

def create_data_file_path_ordered(num: int) -> str:
    return "./data/" + str(num) + "order.txt"

def gen_new_input_file(length: int, biggest_number: int) -> None:
    command = "./out/gen_rand_output.o " + str(length) + " " + str(biggest_number) + " > ./data/temp.txt"
    subprocess.run(command, shell=True)

def gen_new_ordered_input_file(length: int) -> None:
    command = "./out/gen_ordered_output.o " + str(length) + " > ./data/temp.txt"
    subprocess.run(command, shell=True)

def compare_qsort_and_merge_sort():
    quick_sort_times = []
    quick_sort_mo3_times = []
    merge_sort_times = []
    test_data = []
    test_data_length = 0

    while (test_data_length <= 10000):


        # generate random input to file
        gen_new_ordered_input_file(test_data_length)
        # store current test data length
        test_data.append(test_data_length)

        # test time for merge sort save best of three runs
        temp_results = []
        for i in range(3):
            command = create_command(merge_sort_path, test_data_length, temp_input_path)
            time = time_sort(command)
            temp_results.append(time)
        merge_sort_times.append(min(temp_results))

        # test time for quick sort save best of three runs
        temp_results = []
        for i in range(3):
            command = create_command(quick_sort_path, test_data_length, temp_input_path)
            time = time_sort(command)
            temp_results.append(time)
        quick_sort_times.append(min(temp_results))

        # test time for quick sort 
        # command = create_command(quick_sort_mo3_path, test_data_length, temp_input_path)
        # time = time_sort(command)
        # quick_sort_mo3_times.append(time)

        # increment test data length
        test_data_length += 1000

    print(test_data)
    # graph result
    x = test_data
    y1 = quick_sort_times
    pyplot.plot(x, y1, color="red", label="Quick sort", marker="o")

    y2 = merge_sort_times
    pyplot.plot(x, y2, color="blue", label="Merge sort", marker="o")

    # y3 = quick_sort_mo3_times
    # pyplot.plot(x, y3, color="green", label="Quick sort MO3", marker="o")

    pyplot.xlabel("Length of list")
    pyplot.ylabel("Time")
    pyplot.title("Times to sort ordered lists")

    pyplot.legend()
    pyplot.savefig("test.png")


def compare_insertion_and_merge_sort():
    insertion_sort_times = []
    merge_sort_times = []
    test_data = []
    test_data_length = 0

    while (test_data_length < 10000000):

        # generate random input to file
        gen_new_input_file(test_data_length, test_data_length*2)
        # store current test data length
        test_data.append(test_data_length)
        # # test time for insertion sort
        # command = create_command(insertion_sort_path, test_data_length, temp_input_path)
        # time = time_sort(command)
        # insertion_sort_times.append(time)
        # test time for merge sort
        command = create_command(merge_sort_path, test_data_length, temp_input_path)
        time = time_sort(command)
        merge_sort_times.append(time)
        # increment test data length
        test_data_length += 1000000

    print(test_data)
    # graph result
    x = test_data
    # y1 = insertion_sort_times
    # pyplot.plot(x, y1, color="red", label="Insertion sort", marker="o")

    y2 = merge_sort_times
    pyplot.plot(x, y2, color="blue", label="Merge sort", marker="o")

    pyplot.xlabel("Length of list")
    pyplot.ylabel("Time")
    pyplot.title("Times to sort randomly ordered lists")

    pyplot.legend()
    pyplot.savefig("test.png")


def compare_qsort_and_qsort_sort_mo3_unordered():
    quick_sort_times = []
    quick_sort_mo3_times = []
    # merge_sort_times = []
    test_data = []
    test_data_length = 0

    while (test_data_length <= 1000000):
        # generate random input to file
        gen_new_input_file(test_data_length, test_data_length*2)
        # store current test data length
        test_data.append(test_data_length)

        # test time for merge sort save best of three runs
        # temp_results = []
        # for i in range(3):
        #     command = create_command(merge_sort_path, test_data_length, temp_input_path)
        #     time = time_sort(command)
        #     temp_results.append(time)
        # merge_sort_times.append(min(temp_results))

        # test time for quick sort save best of three runs
        temp_results = []
        for i in range(3):
            command = create_command(quick_sort_path, test_data_length, temp_input_path)
            time = time_sort(command)
            temp_results.append(time)
        quick_sort_times.append(min(temp_results))

        # test time for quick sort mo3 save best of three runs
        temp_results = []
        for i in range(3):
            command = create_command(quick_sort_mo3_path, test_data_length, temp_input_path)
            time = time_sort(command)
            temp_results.append(time)
        quick_sort_mo3_times.append(min(temp_results))

        # increment test data length
        test_data_length += 10000

    print(test_data)
    # graph result
    x = test_data
    y1 = quick_sort_times
    pyplot.plot(x, y1, color="red", label="Quick sort", marker="o")

    # y2 = merge_sort_times
    # pyplot.plot(x, y2, color="blue", label="Merge sort", marker="o")

    y3 = quick_sort_mo3_times
    pyplot.plot(x, y3, color="green", label="Quick sort MO3", marker="o")

    pyplot.xlabel("Length of list")
    pyplot.ylabel("Time")
    pyplot.title("Times to sort randomly ordered lists")

    pyplot.legend()
    pyplot.savefig("test.png")


def compare_qsort_and_qsort_sort_mo3_ordered():
    quick_sort_times = []
    quick_sort_mo3_times = []
    # merge_sort_times = []
    test_data = []
    test_data_length = 0

    while (test_data_length <= 20000):
        # generate random input to file
        gen_new_ordered_input_file(test_data_length)
        # store current test data length
        test_data.append(test_data_length)

        # test time for merge sort save best of three runs
        # temp_results = []
        # for i in range(3):
        #     command = create_command(merge_sort_path, test_data_length, temp_input_path)
        #     time = time_sort(command)
        #     temp_results.append(time)
        # merge_sort_times.append(min(temp_results))

        # test time for quick sort save best of three runs
        temp_results = []
        for i in range(3):
            command = create_command(quick_sort_path, test_data_length, temp_input_path)
            time = time_sort(command)
            temp_results.append(time)
        quick_sort_times.append(min(temp_results))

        # test time for quick sort mo3 save best of three runs
        temp_results = []
        for i in range(3):
            command = create_command(quick_sort_mo3_path, test_data_length, temp_input_path)
            time = time_sort(command)
            temp_results.append(time)
        quick_sort_mo3_times.append(min(temp_results))

        # increment test data length
        test_data_length += 1000

    print(test_data)
    # graph result
    x = test_data
    y1 = quick_sort_times
    pyplot.plot(x, y1, color="red", label="Quick sort", marker="o")

    # y2 = merge_sort_times
    # pyplot.plot(x, y2, color="blue", label="Merge sort", marker="o")

    y3 = quick_sort_mo3_times
    pyplot.plot(x, y3, color="green", label="Quick sort MO3", marker="o")

    pyplot.xlabel("Length of list")
    pyplot.ylabel("Time")
    pyplot.title("Times to sort ordered lists")

    pyplot.legend()
    pyplot.savefig("test.png")


def compare_merge_cutoff_times():
    cutoff_sort_times = []
    cutoff_list = []
    
    # test_data = []
    test_data_length = 50000000

    # while (test_data_length < 1000):
    # reset cutoff
    cutoff = 0
    # generate random input to file
    gen_new_input_file(test_data_length, test_data_length*2)

    while (cutoff <= 30):
        # store current test data length
        cutoff_list.append(cutoff)

        command = "/usr/bin/time -f '%U' ./out/merge_sort_rec_cutoff.o " + str(test_data_length) + " " + str(cutoff) + " < data/temp.txt"
        time = time_sort(command)
        cutoff_sort_times.append(time)

        cutoff += 5
    

    # graph result
    x = cutoff_list

    y = cutoff_sort_times
    pyplot.plot(x, y, color="blue", label="Merge sort", marker="o")

    pyplot.xlabel("Cutoff value")
    pyplot.ylabel("Time")
    pyplot.title("Time to sort randomly ordered lists of length " + str(test_data_length))

    pyplot.legend()
    pyplot.savefig("test.png")

# def compare_insertion_and_merge_sort():
#     insertion_sort_times = []
#     merge_sort_times = []
#     for num in data_available_merge_insertion:
#         command = create_command(insertion_sort_path, num, create_data_file_path(num))
#         time = time_sort(command)
#         insertion_sort_times.append(time)
#     for num in data_available_merge_insertion:
#         command = create_command(merge_sort_path, num, create_data_file_path(num))
#         time = time_sort(command)
#         merge_sort_times.append(time)
#     # graph result
#     x = data_available_merge_insertion
#     y1 = insertion_sort_times
#     pyplot.plot(x, y1, color="red", label="Insertion sort", marker="o")
#     y2 = merge_sort_times
#     pyplot.plot(x, y2, color="blue", label="Merge sort", marker="o")
#     pyplot.xlabel("Length of list")
#     pyplot.ylabel("Time")
#     pyplot.title("Times to sort randomly ordered lists")
#     pyplot.legend()
#     pyplot.savefig("test.png")


def run(command):
    pass

if __name__ == "__main__":
    # compare_insertion_and_merge_sort()
    # t = time_sort(["/usr/bin/time -f '%U'", "./out/insertion_sort.o", "1000", "<", "./data/1000.txt"])
    # print(t)
    # c = create_command("./out/merge_sort_rec.o", "100", "./data/100.txt")
    # print(c)
    # d = create_data_file_path(1000)
    # print(d)
    # compare_insertion_and_merge_sort()
    # compare_merge_cutoff_times()
    # compare_qsort_and_merge_sort()
    compare_qsort_and_qsort_sort_mo3_unordered()