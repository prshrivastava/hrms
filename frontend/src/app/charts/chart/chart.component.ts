import { Component, OnInit } from '@angular/core';
import * as d3 from 'd3';
import { PositionViewService } from 'src/app/services/positionview.service';
import { PositionView } from 'src/app/domain/position';

@Component({
  selector: 'app-chart',
  templateUrl: './chart.component.html',
  styleUrls: ['./chart.component.scss']
})
export class ChartComponent implements OnInit {

  constructor(private positionViewService: PositionViewService) { }

  ngOnInit() {
    this.positionViewService.getPositionView().subscribe(data => this.renderPositionBarChart(this.computeTotal(data)));
    /*
    let data = [{
      id: 1, name: "Java Developer", hiringManager: "AtulNene", priority: 1,
      appliedCount: "2", shortListCount: "1", interviewingCount: "1", resumeRejectedCount: "1", total:5
    },
    {
      id: 2, name: "Test Developer", hiringManager: "Pankaj4", priority: 0,
      appliedCount: "3", shortListCount: "2", interviewingCount: "2", resumeRejectedCount: "1", total:8
    },
    {
      id: 3, name: "Python Developer", hiringManager: "AtulNene", priority: 1,
      appliedCount: "2", shortListCount: "1", interviewingCount: "1", resumeRejectedCount: "1", total:5
    },
    {
      id: 4, name: "QA Automation", hiringManager: "Pankaj4", priority: 0,
      appliedCount: "3", shortListCount: "2", interviewingCount: "2", resumeRejectedCount: "1", total:8
    }];
    this.renderPositionBarChart(data);
    */
  }

  private computeTotal(data: PositionView[]) {
    return data.map(d => ({...d, total: parseInt(d.appliedCount) + parseInt(d.shortListCount) 
              + parseInt(d.interviewingCount) + parseInt(d.resumeRejectedCount)}));
  }

  renderPositionBarChart(data) {
    let svg = d3.select('#positionbarchart');
    const margin = { top: 20, right: 20, bottom: 30, left: 40 };
    const width = +svg.attr('width') - margin.left - margin.right;
    const height = +svg.attr('height') - margin.top - margin.bottom;
    let g = svg.append('g').attr('transform', 'translate(' + margin.left + ',' + margin.top + ')');

    // set x scale
    let x = d3.scaleBand()
      .rangeRound([0, width - 100]) //helps keep legend away from the bars
      .paddingInner(0.05)
      .align(0.1);

    // set y scale
    let y = d3.scaleLinear()
      .rangeRound([height, 0]);

    // set the colors
    let z = d3.scaleOrdinal()
      .range([ "#FFDD8B","#FFD060", "#BF9C48", "#A6790E", "#BF5B48", "#a05d56", "#d0743c", "#ff8c00"]);

    var keys = ["appliedCount", "shortListCount", "interviewingCount", "resumeRejectedCount"];
    var legendKeys = {appliedCount: "Applied", shortListCount: "Shortlisted",
                     interviewingCount: "Interviewing", resumeRejectedCount: "Resume Rejected"};

    data.sort(function(a, b) { return parseInt(a.id) - parseInt(b.id); });
    x.domain(data.map(function (d) { return d.id; }));
    y.domain([0, d3.max(data, function (d) { return d.total; })]).nice();
    z.domain(keys);

    g.append("g")
      .selectAll("g")
      .data(d3.stack().keys(keys)(data))
      .enter().append("g")
      .attr("fill", function (d) { return z(d.key); })
      .selectAll("rect")
      .data(function (d) { return d; })
      .enter().append("rect")
      .attr("x", function (d) { return x(d.data.id); })
      .attr("y", function (d) { return y(d[1]); })
      .attr("height", function (d) { return y(d[0]) - y(d[1]); })
      .attr("width", x.bandwidth())
      /*.on('mouseover', function () { tooltip.style("display", null); })
      .on('mouseout', function () { tooltip.style("display", "none"); })
      .on("mousemove", function (d) {
        //console.log(d);
        var xPosition = d3.mouse(this)[0] - 5;
        var yPosition = d3.mouse(this)[1] - 5;
        tooltip.attr("transform", "translate(" + xPosition + "," + yPosition + ")");
        tooltip.select("text").text(d[1] - d[0]);
      }) */;

    g.append("g")
      .attr("class", "axis")
      .attr("transform", "translate(0," + height + ")")
      .call(d3.axisBottom(x));

    g.append("g")
      .attr("class", "axis")
      .call(d3.axisLeft(y).ticks(null, "s"))
      .append("text")
      .attr("x", 2)
      .attr("y", y(y.ticks().pop()) + 0.5)
      .attr("dy", "0.32em")
      .attr("fill", "#000")
      .attr("font-weight", "bold")
      .attr("text-anchor", "start");

    var legend = g.append("g")
      //.attr("font-family", "sans-serif")
      .attr("font-size", 10)
      .attr("text-anchor", "end")
      .selectAll("g")
      .data(keys.slice().reverse())
      .enter().append("g")
      .attr("transform", function (d, i) { return "translate(0," + i * 20 + ")"; });

    legend.append("rect")
      .attr("x", width - 19)
      .attr("width", 19)
      .attr("height", 19)
      .attr("fill", z);

    legend.append("text")
      .attr("x", width - 24)
      .attr("y", 9.5)
      .attr("dy", "0.32em")
      .text(function (d) { console.log(d); return legendKeys[d]; });
  }

}
