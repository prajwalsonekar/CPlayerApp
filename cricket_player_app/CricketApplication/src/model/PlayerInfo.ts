export class PlayerInfo {
     externalServerId: string | undefined;
     name: string | undefined;
     country: string | undefined;
     battingStyle: string | undefined;
     bowlingStyle: string | undefined;
     placeOfBirth: string | undefined;
     imageUrl: string | undefined;
     stats:Stat[]|undefined;
}



export class Stat {
    statType: string | undefined;
    matchType: string | undefined;
    statName: string | undefined;
    value: string | undefined;

}
