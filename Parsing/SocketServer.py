import socket
from selenium import webdriver

def parse(id, pw):
    driver = webdriver.Chrome('C:/Users/user/Desktop/chromedriver/chromedriver')

    driver.get("https://www.bible.ac.kr")

    element_login = driver.find_element_by_class_name('school-nav__login')
    element_login.click()

    element_id = driver.find_element_by_name('userid')
    element_pw = driver.find_element_by_name('password')

    element_id.send_keys(id)
    element_pw.send_keys(pw)

    element_loginBt = driver.find_element_by_name('btnSave')

    element_loginBt.click()

    driver.get("http://www.bible.ac.kr/bibleup/StudentMng/SM050.aspx")
    attendance = driver.find_element_by_id('ctl00_ContentPlaceHolder1_Lab_6').text
    driver.close()

    return attendance

while True:
    server_sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server_sock.bind(('127.0.0.1', 9000))
    server_sock.listen(0)

    print('서버 실행중...')


    client_sock, addr = server_sock.accept()
    id = client_sock.recv(65535)
    pw = client_sock.recv(65535)

    result = parse(id.decode(), pw.decode())
    client_sock.send(result.encode())

    client_sock.close()

    print("끝")