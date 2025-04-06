from collections import defaultdict


def warn_city(city_idsmap, attack_cityid):
    # print(f"city_idsmap:{city_idsmap}")
    # print(f"attack_cityid:{attack_cityid}")
    for attack in attack_cityid:
        city_len = len(city_idsmap)
        for map_key in list(city_idsmap.keys()):
            if attack in city_idsmap[map_key]:
                city_idsmap[map_key].remove(attack)
                if not city_idsmap[map_key]:
                    city_idsmap.pop(map_key)
        # print(city_idsmap)
        if len(city_idsmap)<city_len and len(city_idsmap)!=0:
            print(f"Red Alert: City {attack} is lost!")
        if len(city_idsmap)==city_len or len(city_idsmap)==0:
            print(f"City {attack} is lost.")
        if not city_idsmap:
            print("Game Over.")
            break



N, M = list(map(int, input().split()))
city_idsmap = defaultdict(list)
for _ in range(M):
    city_id = list(map(int, input().split()))
    city_idsmap[city_id[0]].append(city_id[1])
    city_idsmap[city_id[1]].append(city_id[0])

k = int(input())
attack_cityid = list(map(int, input().split()))
warn_city(city_idsmap, attack_cityid)