from selenium import webdriver

driver = webdriver.Chrome('C:/Users/user/Desktop/chromedriver/chromedriver')

driver.get("https://www.bible.ac.kr")

element_login = driver.find_element_by_class_name('school-nav__login')
element_login.click()

element_id = driver.find_element_by_name('userid')
element_pw = driver.find_element_by_name('password')

element_id.send_keys("kyh980909")
element_pw.send_keys("dydgh3258")

element_loginBt = driver.find_element_by_name('btnSave')

element_loginBt.click()

driver.get("http://www.bible.ac.kr/bibleup/StudentMng/SM050.aspx")
attendance = driver.find_element_by_id('ctl00_ContentPlaceHolder1_Lab_6')

print("출석일 : "  + attendance.text)

driver.close()