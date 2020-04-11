export class CandidateResume {
    name: string;
    resumeLink: string;
    referral: boolean;
    shortlisted: boolean;
    processed: boolean;
    constructor(name: string, resumeLink: string) {
        this.name = name;
        this.resumeLink = resumeLink;
        this.referral = false;
        this.shortlisted = false;
        this.processed = false;
    }
}