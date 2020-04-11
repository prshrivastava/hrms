export class Candidate {
    id: string;
    name: string;
    referral: boolean;
    referrer: string;
    resumeLink: string;
    state: string;

    constructor(name: string, resumeLink: string){
        this.name = name;
        this.resumeLink = resumeLink;
    }
}
