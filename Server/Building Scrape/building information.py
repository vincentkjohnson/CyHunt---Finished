import requests
from lxml import etree
import re

if __name__ == '__main__':
    file = open("buildings.txt",'w',encoding='utf-8')
    mainUrl = "https://www.fpm.iastate.edu/maps/buildings/building.asp?id="
    for i in range(1,284):
        url = mainUrl+str(i);
        res = requests.Session().get(url)
        tree = etree.HTML(res.text)
        #content = res.content
        #html = etree.HTML(content)
        buildingName = tree.xpath('//*[@id="main-content"]/div/div[1]/div[1]/h1');
        constructedYear = tree.xpath('//*[@id="main-content"]/div/div[1]/div[1]/p');
        address = tree.xpath('//*[@id="main-content"]/div/div[3]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]');
        if(len(address) == 0):
            address = tree.xpath('//*[@id="main-content"]/div/div[2]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]')
        latitude= tree.xpath('//*[@id="main-content"]/div/div[3]/div[1]/div[1]/div[2]/div[1]/div[2]/div[1]');
        if(len(latitude) == 0):
            latitude = tree.xpath('//*[@id="main-content"]/div/div[2]/div[1]/div[1]/div[2]/div[1]/div[2]/div[1]')
        longitude = tree.xpath('//*[@id="main-content"]/div/div[3]/div[1]/div[1]/div[2]/div[1]/div[2]/div[2]');
        if(len(longitude) == 0):
            longitude = tree.xpath('//*[@id="main-content"]/div/div[2]/div[1]/div[1]/div[2]/div[1]/div[2]/div[2]')
        
        print("id ="+ str(i))
        file.write("id = "+str(i))
        print("\t\t")
        file.write("\t\t")
        if(len(buildingName)>0):
            print(buildingName[0].text)
            file.write(buildingName[0].text)
        if(len(constructedYear)>0):
            print(constructedYear[0].text+"  ")
            file.write(constructedYear[0].text+"  ")
        print("\t\t")
        file.write("\t\t")
        if(len(address)>0):
            print(address[0].text+"  ")
            file.write(address[0].text+"  ")
        print("\t\t")
        file.write("\t\t")
        if(len(latitude)>0):
            print(latitude[0].text+"  ")
            file.write(latitude[0].text+"  ")
        print("\t\t")
        file.write("\t\t")
        if(len(longitude)>0):
            print(longitude[0].text+"  ")
            file.write(longitude[0].text+"  ")
        print("\t\n")
        file.write("\t\n")
        print("\t\n")
        file.write("\t\n")
        #file.write("Building: " + buildingName + "\tConstruted year" + constrctedYear +"\tAddress " + address+
              # "\tLatitude"+ latitude+ "\tLongitude" + longitude+"\t\
    file.close()
    
