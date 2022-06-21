import { PlayerInfo } from "src/model/PlayerInfo";
import { PlayerData, StatData } from './constant';
import { Stat } from '../../model/PlayerInfo';
export class PlayerUtil{
    public convertToPlayer(data:PlayerData):PlayerInfo{
        const playerInfo = new PlayerInfo();
        playerInfo.externalServerId = data.id;
        playerInfo.name = data.name;
        playerInfo.battingStyle = data.battingStyle;
        playerInfo.bowlingStyle = data.bowlingStyle;
        playerInfo.placeOfBirth = data.placeOfBirth;
        playerInfo.country = data.country;
        playerInfo.imageUrl = data.playerImg;
        playerInfo.stats= this.convertToStats(data.stats);
        return playerInfo;
    }

    convertTOStat(data : StatData):Stat{
        const stat = new Stat();
        stat.statType = data.fn;
        stat.matchType = data.matchtype;
        stat.statName= data.stat;
        stat.value = data.value;
        return stat;
    }
    
    convertToStats(data : StatData[] | undefined ):Stat[] {
        if(!data){
            return [];
        }
        const stats  : Stat[] =[];
        for(let statdata of data ){
            let stat = this.convertTOStat(statdata);
            stats.push(stat);
        }
        return stats;
    }

    convertToPlayers(playersData:PlayerData[]):PlayerInfo[]{
        // const data = playersResult;
        const desire:PlayerInfo[] = [];
        for(let playerData of playersData){
            let playerInfo=this.convertToPlayer(playerData);
            desire.push(playerInfo);
        }
        return desire;
    }

    
}